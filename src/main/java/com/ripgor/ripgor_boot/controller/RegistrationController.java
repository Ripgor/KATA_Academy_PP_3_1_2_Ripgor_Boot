package com.ripgor.ripgor_boot.controller;

import com.ripgor.ripgor_boot.model.Role;
import com.ripgor.ripgor_boot.model.User;

import com.ripgor.ripgor_boot.service.UserService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping
    public String createUser(@Valid User user, BindingResult result, Model model) {
        User userFromDB = userService.findUserByName(user.getName());

        if (result.hasErrors()) {
            model.addAttribute(user);
            return "registration";
        }
        if (userFromDB != null) {
            model.addAttribute("userExistsError", "User exists");
            return "registration";
        }

        User userToSave = new User();
        userToSave.setName(user.getName());
        userToSave.setEmail(user.getEmail());
        userToSave.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userToSave.setRoles(Collections.singleton(Role.ROLE_USER));

        userService.saveUser(userToSave);

        return "redirect:/login";
    }
}
