package kz.sqq.recognition.facerecognition.org.repository;

import kz.sqq.recognition.facerecognition.org.form.scheduleResponse.ScheduleInterface;
import kz.sqq.recognition.facerecognition.org.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s where s.worker.id=:id and (s.recognizeDate >=:startDate and s.recognizeDate<=:endDate) order by s.recognizeDate desc ")
    List<Schedule> getAllScheduleByWorkerAndPeriod(Long id, Date startDate, Date endDate);

    @Query(nativeQuery = true, value = "select count(distinct (w.id)) from schedule s inner join worker w on s.worker_id=w.id and w.company_id=:companyId " +
            "where   s.recognize_date>= CURRENT_DATE")
    int getWorkersAtWork(Long companyId);

    @Query(nativeQuery = true, value = "select s1.recognize_date as arrivedDate,s2.date,time(s1.recognize_date) as time," +
            "if(time(s1.recognize_date)>:start_time,false,true) as late,s1.worker_id as workerId\n" +
            "from schedule s1 inner join\n" +
            "(select date(recognize_date) as date, min(recognize_date) as min_date,worker_id\n" +
            "from schedule\n" +
            " where worker_id=:workerId\n" +
            "group by date(recognize_date) ) s2\n" +
            "on s1.worker_id=s2.worker_id\n" +
            "where s1.recognize_date = s2.min_date \n" +
            "and s1.recognize_date >=:createdAt - INTERVAL 30 DAY  \n" +
            "ORDER BY s1.recognize_date  DESC")
    List<ScheduleInterface> getAttendanceByWorkerId(Long workerId, LocalDateTime start_time, Date createdAt);
}
