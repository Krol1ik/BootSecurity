package com.example.bootsecurity.controller;

import com.example.bootsecurity.model.Messages;
import com.example.bootsecurity.model.User;
import com.example.bootsecurity.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private MessagesRepository messagesRepository;

    @GetMapping("/")
    public String greeting() {

        return "greeting";
    }

    @GetMapping("/main")
    public String sayHello(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        Iterable<Messages> messages = messagesRepository.findAll();
        if(filter != null && !filter.isEmpty()){
            messages = messagesRepository.findByTag(filter);
        } else {
            messages = messagesRepository.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String addMessages(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Model model){
        Messages message = new Messages(text, tag, user);
        messagesRepository.save(message);
        Iterable<Messages> messages = messagesRepository.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }


}
