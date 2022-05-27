package com.boraozdogan.kekikpanel.controller;

import com.boraozdogan.kekikpanel.api.NotesRoute;
import com.boraozdogan.kekikpanel.model.NoteRequestModel;
import com.boraozdogan.kekikpanel.repository.NoteRepository;
import com.boraozdogan.kekikpanel.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserPanelController {
    Logger logger = LoggerFactory.getLogger(UserPanelController.class);

    @Value("${boz.app.name}")
    private String appName;
    @Value("${boz.app.apiurl}")
    private String apiURL;

    @Autowired
    private NotesRoute notesRoute;

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/user/{username}/panel")
    public String userPanel(ModelMap model, @PathVariable String username) {
        if(username == null) {
            return "redirect:/";
        }

        model.addAttribute("userNotes", noteRepository.findByOwner(username));
        model.addAttribute("allNotes", noteRepository.findAll());

        return "user_panel";
    }

    @PostMapping("/user/createnote")
    public String createNote(
            @RequestParam("owner") String owner,
            @RequestParam("body") String body
    ) {
//        // NOTE(bora): Make internal API call over HTTP
//        var restTemplate = new RestTemplate();
//        var response = restTemplate.postForObject(
//                apiURL + "/tasks",
//                Map.of("owner", owner,
//                        "body", body),
//                Note.class);

        // NOTE(bora): Call internal API directly
        var response = notesRoute.newNote(
                new NoteRequestModel(owner, body));

        logger.info("Record added: {}", response);
        return "user_panel";
    }
}
