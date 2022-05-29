package com.boraozdogan.kekikpanel.api;

import com.boraozdogan.kekikpanel.api.dto.NoteBodyDTO;
import com.boraozdogan.kekikpanel.model.Note;
import com.boraozdogan.kekikpanel.api.dto.NoteDTO;
import com.boraozdogan.kekikpanel.repository.NoteRepository;
import com.boraozdogan.kekikpanel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class NotesRoute {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/notes")
    public List<Note> all() {
        var result = new ArrayList<Note>();
        noteRepository.findAll().forEach(result::add);

        return result;
    }
    @GetMapping("/api/notes/{id}")
    public Note one(@PathVariable int id) {
        return noteRepository.findById(id).orElse(null);
    }

    @PostMapping("/api/notes")
    public Note newNote(@Valid @RequestBody NoteDTO noteRequest) {
        var userOpt = userRepository.findById(noteRequest.owner());
        if(userOpt.isEmpty()) {
            return  null;
        }

        var user = userOpt.get();
        var note = new Note(
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
        var noteOpt = noteRepository.findById(id);
        if(noteOpt.isPresent()) {
            var note = noteOpt.get();
            note.setBody(noteBody.body());

            return noteRepository.save(note);
        } else {
            throw new IllegalStateException("Note not found.");
        }
    }

    @DeleteMapping("/api/notes/{id}")
    public void deleteNote(@PathVariable int id) {
        noteRepository.deleteById(id);
    }
}
