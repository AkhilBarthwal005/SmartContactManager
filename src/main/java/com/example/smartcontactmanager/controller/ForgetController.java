package com.example.smartcontactmanager.controller;

import com.example.smartcontactmanager.helper.Message;
import com.example.smartcontactmanager.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class ForgetController {

    Random random = new Random(1000);
    @Autowired
    private EmailService emailService;

    @GetMapping("/forget")
    public String forgetPasswordPage(Model model){
        model.addAttribute("title","Forget Password - Smart Contact Manager");
        return "forgetPassword";
    }
    @PostMapping("/send-otp")
    public String forgetPassword(@RequestParam("email") String email, Model model, HttpSession session){
        model.addAttribute("title","Otp send - Smart Contact Manager");
        // generating random number..
        int otp = random.nextInt(999999);
        model.addAttribute("email_id",email);

        boolean flag = emailService.sendEmail(email, "Forget Password OTP from SCM", "OTP : " + otp);
        if(flag){
            session.setAttribute("msg",new Message("OTP send successfully to "+email , "alert-success"));
            model.addAttribute("otp","otp generated successfully");
            session.setAttribute("opt",otp);
            System.out.println(email);
            System.out.println(otp);
            return "forgetPassword";
        }
        else{
         session.setAttribute("msg",new Message("Please enter correct email id" , "alert-danger"));
         return "redirect:/forget";
        }
    }
}
