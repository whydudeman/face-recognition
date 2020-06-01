package kz.sqq.recognition.facerecognition.org.repository;

import kz.sqq.recognition.facerecognition.org.model.Company;
import kz.sqq.recognition.facerecognition.org.model.User;
import kz.sqq.recognition.facerecognition.org.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkerRepo extends JpaRepository<Worker, Long> {
    Optional<Worker> findByMongoId(String mongoId);

    List<Worker> getAllByCompany(Company company);

    @Query("SELECT w FROM Worker w where w.company.id=:id and " +
            "(w.name like %:field% or w.company.name like %:field% or " +
            "w.middleName like %:field% or w.surname like %:field% ) order by w.name")
    List<Worker> getAllByCompanyAndBySearchParameter(Long id, String field);


    Optional<Worker> findByIdAndCompany(Long id, Company company);
}
