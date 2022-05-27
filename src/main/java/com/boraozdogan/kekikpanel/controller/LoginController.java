package com.boraozdogan.kekikpanel.controller;

import com.boraozdogan.kekikpanel.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    @Value("${boz.app.name}")
    private String appName;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("Title",
                String.format("%s - Giriş", appName));

        return "login";
    }

    @PostMapping("/login")
    public String loginPost(
            Model model,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        // TODO(bora): Validate login.
        System.out.printf("[LoginController#loginPost]:: loginInfo.username: %s%n", username);
        System.out.printf("[LoginController#loginPost]:: loginInfo.password: %s%n", password);

        model.addAttribute("username", username);
        model.addAttribute("tasks",
                List.of(
                    new Task(1, "Do this", LocalDate.now(), username),
                    new Task(3, "Do that", LocalDate.of(2022, 05, 27), username)
                ));

        return "listtasks";
    }
}
