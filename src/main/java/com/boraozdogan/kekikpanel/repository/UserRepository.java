package com.boraozdogan.kekikpanel.repository;

import com.boraozdogan.kekikpanel.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository
        extends CrudRepository<User, String> {
}
