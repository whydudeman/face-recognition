package kz.sqq.recognition.facerecognition.org.controller;

import kz.sqq.recognition.facerecognition.org.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "/main/resources/templates/auth/index.html";
    }

    @RequestMapping("login")
    private String login(Model model) {
        return "auth/login";
    }

    @RequestMapping("login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "auth/login";
    }

    @GetMapping("/main")
    private String getMainPage(Model model) {
        return "auth/index";
    }

}
