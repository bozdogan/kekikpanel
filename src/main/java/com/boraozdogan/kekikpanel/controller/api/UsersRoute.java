package com.boraozdogan.kekikpanel.controller.api;

import com.boraozdogan.kekikpanel.model.User;
import com.boraozdogan.kekikpanel.model.UserRequestModel;
import com.boraozdogan.kekikpanel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsersRoute {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/users")
    public List<User> all() {
        var result = new ArrayList<User>();
        userRepository.findAll().forEach(result::add);

        return result;
    }

    @PostMapping("/api/users")
    public User newUser(@RequestBody UserRequestModel userRequestModel) {
        var user = new User(
                userRequestModel.username(),
                userRequestModel.password(),
                userRequestModel.isAdmin());

        userRepository.save(user);
        return user;
    }

    @PutMapping("/api/users/{username}")
    public User replaceUser(
            @PathVariable String username,
            @RequestBody UserRequestModel userRequestModel
    ) {
        var userOpt = userRepository.findById(username);
        if(userOpt.isPresent()) {
            var user = userOpt.get();
            user.setPwHash(userRequestModel.password());
            user.setAdmin(userRequestModel.isAdmin());

            return userRepository.save(user);
        } else {
            return newUser(new UserRequestModel(
                    username,
                    userRequestModel.password(),
                    userRequestModel.isAdmin()
            ));
        }
    }

    @DeleteMapping("/api/users/{username}")
    public void deleteUser(@PathVariable String username) {
        userRepository.deleteById(username);
    }
}
