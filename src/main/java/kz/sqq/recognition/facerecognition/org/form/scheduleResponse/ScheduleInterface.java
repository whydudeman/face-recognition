package kz.sqq.recognition.facerecognition.org.form.scheduleResponse;

import java.time.LocalDateTime;
import java.util.Date;

public interface ScheduleInterface {
    Long getWorkerId();
    Date getArriveDate();
    LocalDateTime getTime();
    Boolean getLate();
}
