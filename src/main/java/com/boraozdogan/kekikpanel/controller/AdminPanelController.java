package com.boraozdogan.kekikpanel.controller;

import com.boraozdogan.kekikpanel.repository.NoteRepository;
import com.boraozdogan.kekikpanel.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminPanelController {
    Logger logger = LoggerFactory.getLogger(AdminPanelController.class);

    @Value("${boz.app.name}")
    private String appName;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/admin/panel")
    public String adminPanel(Model model, HttpServletRequest request) {
        var activeUser = (String) request.getSession().getAttribute("activeUser");
        if(activeUser == null) {
            logger.info("No active user. Redirecting to login page.");
            return "redirect:/login";
        }

        var userOpt = userRepository.findById(activeUser);
        if(userOpt.isEmpty()) {
            logger.warn("Session user does not exist in DB!");
            return "redirect:/login";
        }

        var user = userOpt.get();
        if(!user.isAdmin()) {
            return "redirect:/panel";
        }

        model.addAttribute("userNotes", noteRepository.findByOwner(user));
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("allNotes", noteRepository.findAll());

        model.addAttribute("Title", String.format("%s - Admin Panel", appName));
        return "admin_panel";
    }
}
