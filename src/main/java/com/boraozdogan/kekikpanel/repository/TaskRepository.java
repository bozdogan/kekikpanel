package com.boraozdogan.kekikpanel.repository;

import com.boraozdogan.kekikpanel.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository
        extends CrudRepository<Task, Integer> {

    List<Task> findByOwner(String owner);
}
