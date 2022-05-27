package com.boraozdogan.kekikpanel.repository;

import com.boraozdogan.kekikpanel.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NoteRepository
        extends CrudRepository<Note, Integer> {

    List<Note> findByOwner(String owner);
}
