package com.example.bootsecurity.controller;

import com.example.bootsecurity.model.Role;
import com.example.bootsecurity.model.User;
import com.example.bootsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping ("/registration")
    public String addUser (User user, Model model){
        User userFromBD = userRepository.findByUsername(user.getUsername());
        if(userFromBD != null){
            model.addAttribute("messages", "User exist!");
            return "registration";
        } else {
            user.setRoles(Collections.singleton(Role.USER));
            user.setActive(true);
            userRepository.save(user);

            //Создание админа для тестов
            User admin = new User();
            admin.setRoles(Collections.singleton(Role.ADMIN));
            admin.setUsername("admin");
            admin.setPassword("pass");
            admin.setActive(true);
            userRepository.save(admin);

            return "redirect:login";
        }
    }
}
