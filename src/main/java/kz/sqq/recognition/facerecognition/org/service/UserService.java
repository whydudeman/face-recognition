package kz.sqq.recognition.facerecognition.org.service;

import kz.sqq.recognition.facerecognition.org.form.RegistrationForm;
import kz.sqq.recognition.facerecognition.org.model.Company;
import kz.sqq.recognition.facerecognition.org.model.User;
import kz.sqq.recognition.facerecognition.org.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User getByUsername(String username) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        return userOptional.orElse(null);
    }

    public User create(RegistrationForm regForm) {
        User user = new User();
        System.out.println("HELLO");
        String password = passwordEncoder.encode(regForm.getPassword());
        user.setPassword(password);
        user.setUsername(regForm.username);
        user.setName(regForm.name);
        user.setSurname(regForm.surname);
        Company company;
        company = companyService.createCompanyWithRegForm(regForm);

        user.setCompany(company);
        return userRepo.save(user);
    }
}
