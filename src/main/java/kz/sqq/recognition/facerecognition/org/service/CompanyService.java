package kz.sqq.recognition.facerecognition.org.service;

import kz.sqq.recognition.facerecognition.auth.UserDetailsImpl;
import kz.sqq.recognition.facerecognition.org.form.RegistrationForm;
import kz.sqq.recognition.facerecognition.org.model.Company;
import kz.sqq.recognition.facerecognition.org.model.User;
import kz.sqq.recognition.facerecognition.org.repository.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private UserService userService;

    public Company getById(Long companyId) {
        return companyRepo.findById(companyId).orElseThrow(()->new RuntimeException("NO_COMPANY_WITH_GIVEN_ID"));
    }

    public Company createCompanyWithRegForm(RegistrationForm regForm) {
        Company company=new Company();
        company.setName(regForm.name);
        company.setStart_hour(regForm.start_hour);
        company.setMongoName(regForm.name.toLowerCase());
        return companyRepo.save(company);
    }

    private User getAuthenticatedUser() {
        UserDetailsImpl userDetails=(UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getByUsername(userDetails.getUsername());
    }

    public Company getCompanyOfAuthenticatedUser(){
        return getAuthenticatedUser().getCompany();
    }


}
