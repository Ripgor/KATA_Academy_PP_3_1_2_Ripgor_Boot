package com.ripgor.ripgor_boot.controller;

import com.ripgor.ripgor_boot.model.Role;
import com.ripgor.ripgor_boot.model.User;
import com.ripgor.ripgor_boot.service.UserService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "userList";
    }

    @GetMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);

        return "redirect:/admin";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id") int id, Model model) {

        User userToUpdate = userService.findUser(id);
        model.addAttribute("user", userToUpdate);

        return "user-update";
    }

    @PatchMapping("/user-update")
    public String updateUser(@Valid User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "user-update";
        }

        userService.updateUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/user-create")
    public String formForCreateUser(Model model){
        model.addAttribute(new User());
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(@Valid User user, BindingResult result, Model model) {
        User userFromDB = userService.findUserByName(user.getName());

        if (result.hasErrors()) {
            model.addAttribute(user);
            return "user-create";
        }
        if (userFromDB != null) {
            model.addAttribute("message", "User exists");
            return "user-create";
        }

        User userToSave = new User();
        userToSave.setName(user.getName());
        userToSave.setEmail(user.getEmail());
        userToSave.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userToSave.setRoles(user.getRoles());

        userService.saveUser(userToSave);

        return "redirect:/admin";
    }

}
