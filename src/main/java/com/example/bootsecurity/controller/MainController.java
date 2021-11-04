package com.example.bootsecurity.controller;

import com.example.bootsecurity.model.Messages;
import com.example.bootsecurity.model.User;
import com.example.bootsecurity.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private MessagesRepository messagesRepository;

    @Value("${upload.path}")  //данная аннатация ищет в properties "upload.path" и вставляет в переменную ниже
    private String uploadPath;

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
            @RequestParam String tag, Model model,
            @RequestParam ("file")MultipartFile file) throws IOException {
        Messages message = new Messages(text, tag, user);

        if(file != null && !file.getOriginalFilename().isEmpty()){ // если файл не равен 0, то мы его добавим в класс (Messages)
            File uploadDir = new File(uploadPath);

            if(uploadDir.exists()){   //Если uploadDir не существует, то мы ее создаем
                uploadDir.mkdir();
            }
            //обезопасили себя от коолизии и создаем уникальное имя файла
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));   //загружаем наш файл
            message.setFilename(resultFilename);
        }

        messagesRepository.save(message);
        Iterable<Messages> messages = messagesRepository.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }


}
