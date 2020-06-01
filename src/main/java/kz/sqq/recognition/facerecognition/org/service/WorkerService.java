package kz.sqq.recognition.facerecognition.org.service;

import kz.sqq.recognition.facerecognition.auth.UserDetailsImpl;
import kz.sqq.recognition.facerecognition.org.form.WorkerForm;
import kz.sqq.recognition.facerecognition.org.form.scheduleResponse.ScheduleInterface;
import kz.sqq.recognition.facerecognition.org.model.Company;
import kz.sqq.recognition.facerecognition.org.model.Schedule;
import kz.sqq.recognition.facerecognition.org.model.User;
import kz.sqq.recognition.facerecognition.org.model.Worker;
import kz.sqq.recognition.facerecognition.org.repository.WorkerRepo;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepo workerRepo;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;

    private static String updateRequest = "http://0.0.0.0:2998/update";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Value("${file.upload.profileImage}")
    private String UPLOAD_DIR;

    public Worker getWorkerByMongoId(String mongoId) {
        return workerRepo.findByMongoId(mongoId).orElseGet(() -> new Worker(mongoId));
    }


    public void uploadImageGetPath(MultipartFile[] files, Long id) {
        Worker worker = workerRepo.findById(id).orElseThrow(() -> new RuntimeException("NO_WORKER_FOUND"));
        String mongoName=companyService.getCompanyOfAuthenticatedUser().getMongoName();
        String workerImagesDirectory = UPLOAD_DIR + mongoName + "/" + worker.getMongoId();
        System.out.println(workerImagesDirectory);
        System.out.println("uploadIsd sadsdas");
        new File(workerImagesDirectory).mkdirs();
        for (MultipartFile file : files) {
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = String.valueOf(uuid);
            System.out.println(uuid);
            Path filepath = Paths.get(workerImagesDirectory + "/" + randomUUIDString + Objects.requireNonNull(file.getOriginalFilename())
                    .substring(file.getOriginalFilename()
                            .lastIndexOf(".")));
            System.out.println(filepath);
            System.out.println("uploadImageGetPath ");
            try (OutputStream os = Files.newOutputStream(filepath)) {
                System.out.println("saving");
                os.write(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JSONObject postRequest = new JSONObject();
        postRequest.put("action", "update_facebank");
        postRequest.put("company", worker.getCompany().getMongoName());
        httpPost(updateRequest, postRequest.toJSONString());
        System.out.println("Successfully saved images");
    }

    public String httpPost(String url, String json) {
        String result = "{}";
        HttpPost httpPost = new HttpPost(url);
        HttpEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new EntityExistsException("no_connection_with_flask_server");
    }

    public List<Worker> getAll() {
        User user = getAuthenticatedUser();
        return workerRepo.getAllByCompany(user.getCompany());
    }

    private User getAuthenticatedUser() {
        UserDetailsImpl userDetails=(UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getByUsername(userDetails.getUsername());
    }

    public List<Worker> getAllBySearchText(String field) {
        User user = getAuthenticatedUser();
        return workerRepo.getAllByCompanyAndBySearchParameter(user.getCompany().getId(), field);
    }

    public Worker getById(Long id) {
        return workerRepo.findByIdAndCompany(id, getAuthenticatedUser()
                .getCompany())
                .orElseThrow(
                        () -> new EntityExistsException("NO_USER_IS_IN_COMPANY_OR_DOESNT_EXISTS"));

    }

    public List<Schedule> getArrivesOfWorkerByPeriod(Worker worker, Integer period) {
        return scheduleService.getScheduleByWorkerAndPeriod(worker.getId(),period);
    }

    public void createWorker(WorkerForm workerForm) {
        Worker worker=new Worker();
        worker.setBirthday(workerForm.birthday);
        worker.setMiddleName(workerForm.middleName);
        worker.setName(workerForm.name);
        worker.setSurname(workerForm.surname);
        worker.setCompany(companyService.getById(getAuthenticatedUser().getCompany().getId()));
        worker.setMongoId(workerForm.mongoId);
        workerRepo.save(worker);
    }

    public void updateFromForm(Long id, WorkerForm workerForm) {
        Worker worker=getById(id);
        worker.setMongoId(workerForm.mongoId);
        worker.setSurname(workerForm.surname);
        worker.setName(workerForm.name);
        worker.setMiddleName(workerForm.middleName);
        worker.setBirthday(workerForm.birthday);
        workerRepo.save(worker);
    }

    public List<ScheduleInterface> getScheduleOfWorker(Worker worker){
        return scheduleService.getAttendanceOfWorker(worker.getId(),worker.getCompany().getStart_hour(),worker.getCreatedAt());
    }
}
