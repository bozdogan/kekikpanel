package com.boraozdogan.kekikpanel.controller;

import com.boraozdogan.kekikpanel.repository.NoteRepository;
import com.boraozdogan.kekikpanel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminPanelController {
    @Value("${boz.app.name}")
    private String appName;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/admin/{username}/panel")
    public String adminPanel(ModelMap model, @PathVariable String username) {
        var userOpt = userRepository.findById(username);
        if(userOpt.isEmpty()) {
            return "redirect:/";
        }

        var user = userOpt.get();
        if(!user.isAdmin()) {
            return String.format("redirect:/user/%s/panel", username);
        }

        model.addAttribute("userNotes", noteRepository.findByOwner(user));
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("allNotes", noteRepository.findAll());

        return "admin_panel";
    }
}
