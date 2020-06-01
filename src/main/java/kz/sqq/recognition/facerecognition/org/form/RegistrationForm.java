package kz.sqq.recognition.facerecognition.org.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class RegistrationForm {
    @NotBlank
    @Min(4)
    public String username;
    @NotBlank
    @Min(4)
    public String password;
    @NotBlank
    @Min(4)
    public String name;
    @NotBlank
    @Min(4)
    public String surname;
    public Long companyId;
    @NotBlank
    @Min(4)
    public String companyName;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalDateTime start_hour;

    public LocalDateTime getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(LocalDateTime start_hour) {
        this.start_hour = start_hour;
    }

    public RegistrationForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
