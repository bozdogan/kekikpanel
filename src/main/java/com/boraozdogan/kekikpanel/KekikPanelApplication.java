package com.boraozdogan.kekikpanel;

import com.boraozdogan.kekikpanel.model.User;
import com.boraozdogan.kekikpanel.repository.TaskRepository;
import com.boraozdogan.kekikpanel.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class KekikPanelApplication {

	public static void main(String[] args) {

		var ctx = SpringApplication.run(KekikPanelApplication.class, args);

		// NOTE(bora): Initialize test data
		var userRepository = ctx.getBean(UserRepository.class);

		var someUser = new User("cansimit", "1234", false);
		userRepository.save(someUser);
	}
}
