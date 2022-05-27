package com.boraozdogan.kekikpanel.api;

import com.boraozdogan.kekikpanel.model.Note;
import com.boraozdogan.kekikpanel.model.NoteRequestModel;
import com.boraozdogan.kekikpanel.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class NotesRoute {
    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/api/notes")
    public List<Note> all() {
        var result = new ArrayList<Note>();
        noteRepository.findAll().forEach(result::add);

        return result;
    }

    @PostMapping("/api/notes")
    public Note newNote(@RequestBody NoteRequestModel noteRequest) {
        var note = new Note(
                noteRequest.owner(),
                noteRequest.body(),
                LocalDate.now());

        noteRepository.save(note);
        return note;
    }

    @PutMapping("/api/notes/{id}")
    public Note editNote(
            @PathVariable int id,
            @RequestBody Map<String, Object> taskMap
    ) {
        var noteOpt = noteRepository.findById(id);
        if(noteOpt.isPresent()) {
            var note = noteOpt.get();
            note.setBody(taskMap.get("body").toString());

            return noteRepository.save(note);
        } else {
            throw new IllegalStateException("Note not found.");
        }
    }

    @DeleteMapping("/api/notes/{id}")
    public void deleteUser(@PathVariable int id) {
        noteRepository.deleteById(id);
    }
}
