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
        if(username == null) {
            return "redirect:/";
        }

        model.addAttribute("userNotes", noteRepository.findByOwner(username));
        model.addAttribute("allNotes", noteRepository.findAll());

        return "admin_panel";
    }
}
