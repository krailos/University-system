package ua.com.foxminded.krailo.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String getIndexPage() {
	return "index";
    }

    @GetMapping("admin")
    public String getAdminPage() {
	return "adminPage";
    }

}