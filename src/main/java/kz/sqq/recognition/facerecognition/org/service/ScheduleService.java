package kz.sqq.recognition.facerecognition.org.service;

import kz.sqq.recognition.facerecognition.org.form.scheduleResponse.ScheduleInterface;
import kz.sqq.recognition.facerecognition.org.model.Company;
import kz.sqq.recognition.facerecognition.org.model.Schedule;
import kz.sqq.recognition.facerecognition.org.model.Worker;
import kz.sqq.recognition.facerecognition.org.repository.ScheduleRepo;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;

    public Schedule createSchedule(Date recognizeTime, Worker worker) {
        Schedule schedule=new Schedule();
        schedule.setRecognizeDate(recognizeTime);
        schedule.setWorker(worker);
        return scheduleRepo.save(schedule);
    }

    public List<Schedule> getScheduleByWorkerAndPeriod(Long id, Integer period) {
        Calendar calendar=Calendar.getInstance();
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DATE,-period);
        Date startDate=calendar.getTime() ;
        return scheduleRepo.getAllScheduleByWorkerAndPeriod(id,startDate,endDate);
    }

    public int getCurrentlyAtWork(Company company){
        return scheduleRepo.getWorkersAtWork(company.getId());
    }

    public List<ScheduleInterface> getAttendanceOfWorker(Long workerId, LocalDateTime start_hour, Date createdAt) {
        return scheduleRepo.getAttendanceByWorkerId(workerId,start_hour,createdAt);
    }
}
