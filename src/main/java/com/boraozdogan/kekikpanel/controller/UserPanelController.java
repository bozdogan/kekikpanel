package com.boraozdogan.kekikpanel.controller;

import com.boraozdogan.kekikpanel.api.NotesRoute;
import com.boraozdogan.kekikpanel.api.dto.NoteBodyDTO;
import com.boraozdogan.kekikpanel.api.dto.NoteDTO;
import com.boraozdogan.kekikpanel.model.Note;
import com.boraozdogan.kekikpanel.model.User;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
public class UserPanelController {
    Logger logger = LoggerFactory.getLogger(UserPanelController.class);

    @Value("${boz.app.name}")
    private String appName;

    @Autowired
    private NotesRoute notesRoute;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/panel")
    public String userPanel(ModelMap model, HttpServletRequest request) {
        String activeUser = (String) request.getSession().getAttribute("activeUser");
        if(activeUser == null) {
            logger.info("No active user. Redirecting to login page.");
            return "redirect:/login";
        }

        Optional<User> userOpt = userRepository.findById(activeUser);
        if(!userOpt.isPresent()) {
            logger.warn("Session user does not exist in DB!");
            return "redirect:/login";
        }

        User user = userOpt.get();
        model.addAttribute("activeUser", activeUser);
        model.addAttribute("isUserAdmin", user.isAdmin());
        model.addAttribute("userNotes", noteRepository.findByOwner(user));
        model.addAttribute("allNotes", noteRepository.findAll());

        model.addAttribute("Title", String.format("%s - User Panel", appName));
        return "user_panel";
    }

    @PostMapping("/user/createnote")
    public String createNote(
            @RequestParam("body") String body,
            HttpServletRequest request
    ) {
        String activeUser = (String) request.getSession().getAttribute("activeUser");
        if(activeUser == null) {
            logger.info("No active user. Redirecting to login page.");
            return "redirect:/login";
        }

        if(body.trim().length() == 0) {
            return "redirect:/user/panel";
        }

        Note note = notesRoute.newNote(new NoteDTO(activeUser, body));

        logger.info("Record added: {}", note);
        logger.info("Session variable: {}", request.getSession().getAttribute("activeUser"));
        return "redirect:/user/panel";
    }

    @GetMapping("/user/shownote/{id}")
    public String showNote(
            @PathVariable("id") int noteID,
            Model model,
            HttpServletRequest request
    ) {
        String activeUser = (String) request.getSession().getAttribute("activeUser");
        if(activeUser == null) {
            logger.info("No active user. Redirecting to login page.");
            return "redirect:/login";
        }

        Note note = notesRoute.one(noteID);
        if(note == null) {
            logger.info("showNote:: Not found: Note#{}", noteID);
            return "redirect:/user/panel";
        }

        if(!note.getOwner().getUsername().equals(activeUser)
                && !note.getOwner().isAdmin()) {
            logger.info("showNote:: Access violation: User '{}' is not the owner of Note#{} ", activeUser, noteID);
            return "redirect:/user/panel";
        }

        logger.info("Record fetched: {}", note);

        model.addAttribute("activeUser", activeUser);
        model.addAttribute("note", note);
        model.addAttribute("Title", String.format("%s - Show Note", appName));
        return "note_show";
    }

    @GetMapping("/user/editnote/{id}")
    public String editNoteGet(
            @PathVariable("id") int noteID,
            Model model,
            HttpServletRequest request
    ) {
        String activeUser = (String) request.getSession().getAttribute("activeUser");
        if(activeUser == null) {
            logger.info("No active user. Redirecting to login page.");
            return "redirect:/login";
        }

        Note note = notesRoute.one(noteID);
        if(note == null) {
            logger.info("Not found: Note#{}", noteID);
            return "redirect:/user/panel";
        }

        if(!note.getOwner().getUsername().equals(activeUser)
                && !note.getOwner().isAdmin()) {
            logger.info("editNoteGet:: Access violation: User '{}' is not the owner of Note#{} ", activeUser, noteID);
            return "redirect:/user/panel";
        }

        logger.info("Record fetched: {}", note);

        model.addAttribute("activeUser", activeUser);
        model.addAttribute("note", note);
        model.addAttribute("Title", String.format("%s - Edit Note", appName));
        return "note_edit";
    }

    @PostMapping("/user/editnote/{id}")
    public String editNotePost(
            @PathVariable("id") int noteID,
            @RequestParam("body") String body,
            Model model,
            HttpServletRequest request
    ) {
        String activeUser = (String) request.getSession().getAttribute("activeUser");
        if(activeUser == null) {
            logger.info("No active user. Redirecting to login page.");
            return "redirect:/login";
        }

        Note note = notesRoute.editNote(noteID, new NoteBodyDTO(body));
        if(note == null) {
            logger.info("Not found: Note#{}", noteID);
            return "redirect:/user/panel";
        }

        if(!note.getOwner().getUsername().equals(activeUser)
                && !note.getOwner().isAdmin()) {
            logger.info("editNotePut:: Access violation: User '{}' is not the owner of Note#{} ", activeUser, noteID);
            return "redirect:/user/panel";
        }

        logger.info("Record fetched: {}", note);

        model.addAttribute("activeUser", activeUser);
        model.addAttribute("note", note);
        model.addAttribute("Title", String.format("%s - Edit Note", appName));
        return "note_show";
    }

    @GetMapping("/user/deletenote/{id}")
    public String deleteNoteGet(
            @PathVariable("id") int noteID,
            Model model,
            HttpServletRequest request
    ) {
        String activeUser = (String) request.getSession().getAttribute("activeUser");
        if(activeUser == null) {
            logger.info("No active user. Redirecting to login page.");
            return "redirect:/login";
        }

        Note note = notesRoute.one(noteID);
        if(note == null) {
            logger.info("deleteNote:: Not found: Note#{}", noteID);
            return "redirect:/user/panel";
        }

        if(!note.getOwner().getUsername().equals(activeUser)
                && !note.getOwner().isAdmin()) {
            logger.info("deleteNote:: Access violation: User '{}' is not the owner of Note#{} ", activeUser, noteID);
            return "redirect:/user/panel";
        }

        model.addAttribute("activeUser", activeUser);
        model.addAttribute("note", note);
        model.addAttribute("Title", String.format("%s - Delete Note", appName));
        return "note_confirmdelete";
    }

    @PostMapping("/user/deletenote/{id}")
    public String deleteNotePost(
            @PathVariable("id") int noteID,
            Model model,
            HttpServletRequest request
    ) {
        String activeUser = (String) request.getSession().getAttribute("activeUser");
        if(activeUser == null) {
            logger.info("No active user. Redirecting to login page.");
            return "redirect:/login";
        }

        Note note = notesRoute.one(noteID);
        if(note == null) {
            logger.info("deleteNote:: Not found: Note#{}", noteID);
            return "redirect:/user/panel";
        }

        if(!note.getOwner().getUsername().equals(activeUser)
                && !note.getOwner().isAdmin()) {
            logger.info("deleteNote:: Access violation: User '{}' is not the owner of Note#{} ", activeUser, noteID);
            return "redirect:/user/panel";
        }

        notesRoute.deleteNote(noteID);

        logger.info("Record deleted: {}", noteID);
        return String.format("redirect:/user/shownote/%s", noteID);
    }
}
