package kz.sqq.recognition.facerecognition.org.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String mongoName; //same with company folder name
    private LocalDateTime start_hour;
    @OneToMany(mappedBy = "company")
    private List<Worker> workers;

}
