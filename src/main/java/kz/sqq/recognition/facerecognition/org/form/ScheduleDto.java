package kz.sqq.recognition.facerecognition.org.form;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ScheduleDto {
    @NotNull
    public String id;
    @NotNull
    public String date;
    @NotNull
    public String company;

    public ScheduleDto() {
    }

}
