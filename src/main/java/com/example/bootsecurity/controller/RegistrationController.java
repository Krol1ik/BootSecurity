package com.example.bootsecurity.controller;

import com.example.bootsecurity.model.User;
import com.example.bootsecurity.repository.UserRepository;
import com.example.bootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping ("/registration")
    public String addUser (User user, Model model){
        if(!userService.addUser(user)){
            model.addAttribute("messages", "User exist!");
            return "registration";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/activate/{code}")
    public String activate (Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);

        if(isActivated){
            model.addAttribute("messages", "Activation is successful");
        } else {
            model.addAttribute("messages","Activation code is not found");
        }
        return "login";
    }
}
