package com.boraozdogan.kekikpanel.controller;

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

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Value("${boz.app.name}")
    private String appName;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginGet(Model model) {
        model.addAttribute("Title", String.format("%s - Giriş", appName));
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request
    ) {
        var activeUser = (String) request.getSession().getAttribute("activeUser");
        if(activeUser != null) {
            logger.info("User already logged in: {}", activeUser);
            return "redirect:/panel";
        }

        logger.info("loginPost:: username: {}", username);
        logger.info("loginPost:: password: {}", password);

        if(userService.validateLogin(username, password)) {
            request.getSession().setAttribute("activeUser", username);
            return "redirect:/panel";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/panel")
    public String resolveUserType(HttpServletRequest request) {
        var activeUser = (String) request.getSession().getAttribute("activeUser");
        if(activeUser == null) {
            logger.info("Redirecting: /panel -> /login");
            return "redirect:/login";
        }

        if(userService.isAdmin(activeUser)) {
            return "redirect:/admin/panel";
        } else {
            return "redirect:/user/panel";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
