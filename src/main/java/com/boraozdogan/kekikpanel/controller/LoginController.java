package com.boraozdogan.kekikpanel.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @Value("${boz.app.name}")
    private String appName;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("Title",
                String.format("%s - Giriş", appName));

        return "index";
    }
}
