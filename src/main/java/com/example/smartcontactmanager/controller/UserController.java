package com.example.smartcontactmanager.controller;

import com.example.smartcontactmanager.dao.UserRepository;
import com.example.smartcontactmanager.entities.Contact;
import com.example.smartcontactmanager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addCommonDataToModal(Model model , Principal principal){
        /* here Principal is a java security package class in with help of this class object we can get user name after its login*/
        String name = principal.getName();
        // getting user details from the database.
        User user = userRepository.getUserByUserName(name);
        model.addAttribute("user",user);
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model , Principal principal)
    {
        model.addAttribute("title","Dashboard - Smart Contact Manager");
        return "user/dashboard";
    }

    @GetMapping("/add-contact")
    public String openAddFormPage(Model model , Principal principal)
    {
        model.addAttribute("title","Add Contact - Smart Contact Manager");
        model.addAttribute("contact",new Contact());
        return "user/add_contact_form";
    }
}
