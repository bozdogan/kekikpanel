package com.boraozdogan.kekikpanel.controller;

import com.boraozdogan.kekikpanel.repository.NoteRepository;
import com.boraozdogan.kekikpanel.repository.UserRepository;
import com.boraozdogan.kekikpanel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Value("${boz.app.name}")
    private String appName;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(ModelMap model) {
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
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        logger.info("loginInfo.username: {}", username);
        logger.info("loginInfo.password: {}", password);

        if(userService.validateLogin(username, password)) {
            if(userService.isAdmin(username)) {
                // NOTE(bora): This should be done using sessions.
                return String.format("redirect:/admin/%s/panel", username);
            } else {
                return String.format("redirect:/user/%s/panel", username);
            }
        } else {
            return "redirect:/";
        }
    }
}
