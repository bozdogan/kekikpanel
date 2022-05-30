package com.boraozdogan.kekikpanel.api;

import com.boraozdogan.kekikpanel.api.dto.NoteBodyDTO;
import com.boraozdogan.kekikpanel.model.Note;
import com.boraozdogan.kekikpanel.api.dto.NoteDTO;
import com.boraozdogan.kekikpanel.model.User;
import com.boraozdogan.kekikpanel.repository.NoteRepository;
import com.boraozdogan.kekikpanel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class NotesRoute {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/notes")
    public List<Note> all() {
        List<Note> result = new ArrayList<>();
        noteRepository.findAll().forEach(result::add);

        return result;
    }
    @GetMapping("/api/notes/{id}")
    public Note one(@PathVariable int id) {
        return noteRepository.findById(id).orElse(null);
    }
    @GetMapping("/api/notes/of/{username}")
    public List<Note> byOwner(@PathVariable String username) {
        Optional<User> userOpt = userRepository.findById(username);
        if(!userOpt.isPresent()) {
            return null;
        }

        User user = userOpt.get();
        return noteRepository.findByOwner(user);
    }

    @PostMapping("/api/notes")
    public Note newNote(@Valid @RequestBody NoteDTO noteRequest) {
        Optional<User> userOpt = userRepository.findById(noteRequest.owner());
        if(!userOpt.isPresent()) {
            return null;
        }

        User user = userOpt.get();
        Note note = new Note(
                user,
                noteRequest.body(),
                LocalDate.now());

        noteRepository.save(note);
        return note;
    }

    @PutMapping("/api/notes/{id}")
    public Note editNote(
            @PathVariable int id,
            @Valid @RequestBody NoteBodyDTO noteBody
    ) {
        Optional<Note> noteOpt = noteRepository.findById(id);
        if(noteOpt.isPresent()) {
            Note note = noteOpt.get();
            note.setBody(noteBody.body());

            return noteRepository.save(note);
        } else {
            throw new IllegalStateException("Note not found.");
        }
    }

    @DeleteMapping("/api/notes/{id}")
    public void deleteNote(@PathVariable int id) {
        if(noteRepository.findById(id).isPresent()) {
            noteRepository.deleteById(id);
        }
    }
}
