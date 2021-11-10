package com.example.bootsecurity.controller;

import com.example.bootsecurity.model.Role;
import com.example.bootsecurity.model.User;
import com.example.bootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")  //Аннотация пропускает к данным контроллером только админов
    @GetMapping
    public String userList(Model model){
        model.addAttribute("userList", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEdit (@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave (
            @RequestParam String username,   //Получаем имя пользователя
            @RequestParam Map<String, String> form,  //На сервер будут приходить только те данные, которые отмечены флажком в чекбоксе
            @RequestParam ("userId") User user   //Получаем пользователя по его ID
    ){
        userService.save(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("/profile")
    //@AuthenticationPrincipal User user - благодоря этой аннотации мы получаем пользователя из контекста, а не из БД
    public String getProfile (Model model, @AuthenticationPrincipal User user){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String password,
                                @RequestParam String email){
        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }
    }

