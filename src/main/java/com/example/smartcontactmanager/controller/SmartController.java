package com.example.smartcontactmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SmartController {

    @GetMapping("/home")
    public String homePage(Model model){
        model.addAttribute("title","Home - Smart Contact Manager");
        return "home";
    }

    @GetMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("title","About - Smart Contact Manager");
        return "about";
    }
}
