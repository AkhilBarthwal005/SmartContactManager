package com.example.smartcontactmanager.controller;

import com.example.smartcontactmanager.dao.ContactRepository;
import com.example.smartcontactmanager.dao.UserRepository;
import com.example.smartcontactmanager.entities.Contact;
import com.example.smartcontactmanager.entities.User;
import com.example.smartcontactmanager.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

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
    public String saveContactDetails(@Valid @ModelAttribute("contact") Contact contact, BindingResult result, @RequestParam("profilePicture") MultipartFile profile, Model model, Principal principal, HttpSession session)
    {
        try{
            if(result.hasErrors()){
                System.out.println(result);
                model.addAttribute("contact",contact);
                return "user/add_contact_form";
            }

            String name = principal.getName();
            User user = userRepository.getUserByUserName(name);
            System.out.println(contact);

            if(profile.isEmpty()){
                System.out.println("file is empty");
                contact.setImage("default.png");
                session.setAttribute("msg",new Message("Profile Picture is required!!","alert-warning"));
            }
            else{
                File file = new ClassPathResource("static/images/profile").getFile();
                Path path = Paths.get(file.getAbsolutePath() + File.separator +contact.getNickName()+"_"+ profile.getOriginalFilename());
                contact.setImage(contact.getNickName()+"_"+ profile.getOriginalFilename());
                Files.copy(profile.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded");
                session.setAttribute("msg",new Message("Your contact added successfully!!","alert-success"));
                contact.setUser(user);
                user.getContacts().add(contact);
                contactRepository.save(contact);
                model.addAttribute("contact",new Contact());
            }
        }
        catch (Exception e){
            e.printStackTrace();
            session.setAttribute("msg",new Message("Some thing went woring please try again after some time!!","alert-danger"));
        }
        return "user/add_contact_form";
    }

    @GetMapping("/view-contacts/{currentPage}")
    public String openViewContactPage(@PathVariable("currentPage") Integer currentPage ,Model model , Principal principal)
    {
        model.addAttribute("title","View Contact - Smart Contact Manager");
        // getting user
        String name = principal.getName();
        User user= userRepository.getUserByUserName(name);
        // getting contact details
        int perPage=1;
//        currentPage =start with 0
        Pageable pageable = PageRequest.of(currentPage, 1);
        Page<Contact> contacts = contactRepository.findAllContactsByUserId(user.getId(),pageable);
        model.addAttribute("contacts",contacts);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalPage",contacts.getTotalPages());
        return "user/view_contacts";
    }


}
