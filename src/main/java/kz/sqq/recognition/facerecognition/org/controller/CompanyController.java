package kz.sqq.recognition.facerecognition.org.controller;

import kz.sqq.recognition.facerecognition.org.form.CompanyForm;
import kz.sqq.recognition.facerecognition.org.model.Company;
import kz.sqq.recognition.facerecognition.org.service.CompanyService;
import kz.sqq.recognition.facerecognition.org.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping(value = "company/{id}")
    public String getCompanyById(@PathVariable(name = "id") Long id, Model model){
        model.addAttribute("company",companyService.getById(id));
        return "company/index";
    }

    @GetMapping(value = "edit/{id}")
    public String edit(@PathVariable(name = "id") Long id,Model model){
        model.addAttribute("companyForm",new CompanyForm());
        return "company/edit";
    }

    @GetMapping
    public String getCompany(Model model){
        Company company=companyService.getCompanyOfAuthenticatedUser();
        model.addAttribute("company",company);
        model.addAttribute("currentAtWork",scheduleService.getCurrentlyAtWork(company));
        model.addAttribute("lateComings",scheduleService.getCurrentlyAtWork(company));
        return "auth/index";
    }

    @GetMapping(value = "s")
    public String getCompanyS(Model model){
        Company company=companyService.getCompanyOfAuthenticatedUser();
        model.addAttribute("company",company);
        model.addAttribute("currentAtWork",scheduleService.getCurrentlyAtWork(company));
        model.addAttribute("lateComings",scheduleService.getCurrentlyAtWork(company));
        return "company/index";
    }


}
