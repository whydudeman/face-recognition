package kz.sqq.recognition.facerecognition.org.repository;

import kz.sqq.recognition.facerecognition.org.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company,Long> {
}
