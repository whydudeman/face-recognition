package kz.sqq.recognition.facerecognition.org.controller;

import kz.sqq.recognition.facerecognition.org.form.RegistrationForm;
import kz.sqq.recognition.facerecognition.org.model.User;
import kz.sqq.recognition.facerecognition.org.service.CompanyService;
import kz.sqq.recognition.facerecognition.org.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

@Controller
@RequestMapping("registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;

    @ModelAttribute("user")
    public RegistrationForm userRegistrationDto() {
        return new RegistrationForm();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("regForm",userRegistrationDto());
        return "auth/register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("regForm") @Valid RegistrationForm regForm,
                                      BindingResult result) {
        System.out.println("HIHI");
        User existing = userService.getByUsername(regForm.username);
        if (existing != null) {
            throw new EntityExistsException("USER_EXISTS_WITH_THIS_USERNAME");
        }

        System.out.println("HIHI");
        userService.create(regForm);
        return "auth/login";
    }
}