package kz.sqq.recognition.facerecognition.org.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class WorkerForm {
    @NotBlank
    public String name;
    @NotBlank
    public String surname;
    @NotBlank
    public String middleName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date birthday;
    @NotBlank
    public String mongoId;

    public WorkerForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }
}
