package com.boraozdogan.kekikpanel;

import com.boraozdogan.kekikpanel.model.Task;
import com.boraozdogan.kekikpanel.model.User;
import com.boraozdogan.kekikpanel.repository.TaskRepository;
import com.boraozdogan.kekikpanel.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;


@SpringBootApplication
public class KekikPanelApplication {

	public static void main(String[] args) {

		var ctx = SpringApplication.run(KekikPanelApplication.class, args);

		// NOTE(bora): Initialize test data
		var userRepo = ctx.getBean(UserRepository.class);
		var taskRepo = ctx.getBean(TaskRepository.class);

		userRepo.save(new User("admin", "admin", true));
		userRepo.save(new User("cansimit", "1234", false));
		userRepo.save(new User("bora", "1234", false));

		taskRepo.save(new Task("cansimit","Do this", LocalDate.now()));
		taskRepo.save(new Task("admin", "Everything", LocalDate.now()));
        taskRepo.save(new Task("cansimit", "Do that", LocalDate.of(2022, 05, 27)));
		taskRepo.save(new Task("bora", "My first task", LocalDate.of(2022, 05, 26)));
		taskRepo.save(new Task("bora", "My second task", LocalDate.of(2022, 05, 27)));
	}
}
