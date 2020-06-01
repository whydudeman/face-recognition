package kz.sqq.recognition.facerecognition.org.service;

import kz.sqq.recognition.facerecognition.org.form.ScheduleDto;
import kz.sqq.recognition.facerecognition.org.model.Schedule;
import kz.sqq.recognition.facerecognition.org.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FlaskService {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private ScheduleService scheduleService;
    public ResponseEntity getArriveFromFlask(ScheduleDto scheduleDto) {
        Worker worker=workerService.getWorkerByMongoId(scheduleDto.id);
        Date recognizeTime=parseStandardFormat(scheduleDto.date);
        scheduleService.createSchedule(recognizeTime,worker);
        System.out.println(scheduleDto.id+" "+scheduleDto.company+" "+scheduleDto.date);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    public static Date parseStandardFormat(String dateToParse){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return sdf.parse(dateToParse);
        } catch (java.text.ParseException e)
        {
            throw new EntityExistsException("not_right_date_format");
        }
    }
}
