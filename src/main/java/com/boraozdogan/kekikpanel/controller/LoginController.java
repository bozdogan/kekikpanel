package com.boraozdogan.kekikpanel.controller;

import com.boraozdogan.kekikpanel.repository.NoteRepository;
import com.boraozdogan.kekikpanel.repository.UserRepository;
import com.boraozdogan.kekikpanel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Value("${boz.app.name}")
    private String appName;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("Title",
                String.format("%s - Giriş", appName));

        return "login";
    }

    @GetMapping("/login")
    public String redirectLogin() {
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginPost(
            Model model,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        logger.info("loginInfo.username: {}", username);
        logger.info("loginInfo.password: {}", password);


        if(userService.validateLogin(username, password)) {
            model.addAttribute("username", username);
            model.addAttribute("userNotes", noteRepository.findByOwner(username));

            if(userService.isAdmin(username)) {
                model.addAttribute("users", userRepository.findAll());
                model.addAttribute("allNotes", noteRepository.findAll());
                return "admin_panel";
            } else {
                return "user_panel";
            }
        } else {
            return "redirect:/";
        }
    }
}
