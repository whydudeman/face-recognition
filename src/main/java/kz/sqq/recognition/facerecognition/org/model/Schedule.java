package kz.sqq.recognition.facerecognition.org.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "schedule",
        uniqueConstraints = @UniqueConstraint(name = "schedule_worker", columnNames = {"worker_id"}))
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date recognizeDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_id")
    private Worker worker;

    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRecognizeDate() {
        return recognizeDate;
    }

    public void setRecognizeDate(Date recognizeDate) {
        this.recognizeDate = recognizeDate;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
