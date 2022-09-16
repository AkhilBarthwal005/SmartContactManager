package com.example.smartcontactmanager.controller;

import com.example.smartcontactmanager.dao.ContactRepository;
import com.example.smartcontactmanager.dao.UserRepository;
import com.example.smartcontactmanager.entities.Contact;
import com.example.smartcontactmanager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;

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

    @PostMapping("/process-add-contact")
    public String saveContactDetails(@ModelAttribute("contact") Contact contact, @RequestParam("profilePicture") MultipartFile profile, Model model, Principal principal)
    {
        try{
            String name = principal.getName();
            User user = userRepository.getUserByUserName(name);
            System.out.println(contact);

            if(profile.isEmpty()){
                System.out.println("file is empty");
                contact.setImage("default.png");
            }
            else{
                File file = new ClassPathResource("static/images/profile").getFile();
                Path path = Paths.get(file.getAbsolutePath() + File.separator +contact.getNickName()+"_"+ profile.getOriginalFilename());
                contact.setImage(contact.getCId()+"_"+ profile.getOriginalFilename());
                Files.copy(profile.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded");
            }

            contact.setUser(user);
            user.getContacts().add(contact);
            contactRepository.save(contact);
            model.addAttribute("contact",new Contact());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "user/add_contact_form";
    }


}
