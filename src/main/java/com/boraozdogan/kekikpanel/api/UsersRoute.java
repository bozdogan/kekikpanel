package com.boraozdogan.kekikpanel.api;

import com.boraozdogan.kekikpanel.model.User;
import com.boraozdogan.kekikpanel.api.dto.UserDTO;
import com.boraozdogan.kekikpanel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public User newUser(@Valid @RequestBody UserDTO userDTO) {
        var user = new User(
                userDTO.username(),
                userDTO.password(),
                userDTO.isAdmin());

        userRepository.save(user);
        return user;
    }

    @PutMapping("/api/users/{username}")
    public User replaceUser(
            @PathVariable String username,
            @Valid @RequestBody UserDTO userDTO
    ) {
        var userOpt = userRepository.findById(username);
        if(userOpt.isPresent()) {
            var user = userOpt.get();
            user.setPwHash(userDTO.password());
            user.setAdmin(userDTO.isAdmin());

            return userRepository.save(user);
        } else {
            return newUser(new UserDTO(
                    username,
                    userDTO.password(),
                    userDTO.isAdmin()
            ));
        }
    }

    @DeleteMapping("/api/users/{username}")
    public void deleteUser(@PathVariable String username) {
        userRepository.deleteById(username);
    }
}
