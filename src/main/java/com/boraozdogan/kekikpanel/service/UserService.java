package com.boraozdogan.kekikpanel.service;

import com.boraozdogan.kekikpanel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public boolean validateLogin(String username, String password) {
        var user = repository.findById(username);
        return user.isPresent()
                && user.get().getPwHash().equals(password);
    }

    public boolean isAdmin(String username) {
        var user = repository.findById(username);
        return user.isPresent() && user.get().isAdmin();
    }
}
