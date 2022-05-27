package com.boraozdogan.kekikpanel.repository;

import com.boraozdogan.kekikpanel.model.Note;
import com.boraozdogan.kekikpanel.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NoteRepository
        extends CrudRepository<Note, Integer> {

    List<Note> findByOwner(User owner);
}
