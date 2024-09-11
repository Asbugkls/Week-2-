package com.anand.java_project.controller;

import com.anand.java_project.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private List<User> userList = new ArrayList<>(); 
    private final String defaultEmail = "anand@gmail.com";
    private final String defaultPassword = "12345";

    public LoginController() {
        User defaultUser = new User(defaultEmail, defaultPassword);
        userList.add(defaultUser);
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {

                            boolean isValidUser = userList.stream()
                .anyMatch(user -> user.getEmail().equals(email) && user.getPassword().equals(password));

        if (isValidUser) {
            return "redirect:/home"; 
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; }
        

    @PostMapping("/register")
    public String registerUser(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model) {

                                boolean emailExists = userList.stream()
                .anyMatch(user -> user.getEmail().equals(email));

        if (emailExists) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        User newUser = new User(email, password);
        userList.add(newUser);

        return "redirect:/login";
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}