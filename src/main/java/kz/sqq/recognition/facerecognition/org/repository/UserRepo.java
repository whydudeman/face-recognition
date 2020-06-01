package kz.sqq.recognition.facerecognition.org.repository;

import kz.sqq.recognition.facerecognition.org.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
