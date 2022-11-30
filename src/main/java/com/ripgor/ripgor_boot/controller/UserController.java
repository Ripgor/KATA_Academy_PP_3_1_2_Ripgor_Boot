package com.ripgor.ripgor_boot.controller;

import com.ripgor.ripgor_boot.model.User;
import com.ripgor.ripgor_boot.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUser(Principal principal, Model model) {
        int id = userService.findUserByName(principal.getName()).getId();
        User user = userService.findUser(id);

        model.addAttribute("user", user);
        return "user-show";
    }
}
