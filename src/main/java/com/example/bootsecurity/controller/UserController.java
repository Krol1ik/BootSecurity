package com.example.bootsecurity.controller;

import com.example.bootsecurity.model.Role;
import com.example.bootsecurity.model.User;
import com.example.bootsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")  //Аннотация пропускает к данным контроллером только админов
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model){
        model.addAttribute("userList", userRepository.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEdit (@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String userSave (
            @RequestParam String username,   //Получаем имя пользователя
            @RequestParam Map<String, String> form,  //На сервер будут приходить только те данные, которые отмечены флажком в чекбоксе
            @RequestParam ("userId") User user   //Получаем пользователя по его ID
    ){
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())        //Получаем список ролей, чтобы проверить, что они установленны данному пользователю
                                .map(Role::name)
                                .collect(Collectors.toSet());

        user.getRoles().clear();
        for (String key : form.keySet()) {  // проверяем, что наша форма содержит роли для нашего пользователя
            if(roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
        return "redirect:/user";
    }
}
