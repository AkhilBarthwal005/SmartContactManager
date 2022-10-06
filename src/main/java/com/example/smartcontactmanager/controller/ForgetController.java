package com.example.smartcontactmanager.controller;

import com.example.smartcontactmanager.helper.Message;
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
        session.setAttribute("msg",new Message("OTP send successfully to "+email , "alert-success"));
        model.addAttribute("otp","otp generated successfully");
        System.out.println(email);
        System.out.println(otp);
        return "forgetPassword";
    }
}
