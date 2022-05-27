package com.boraozdogan.kekikpanel;

import com.boraozdogan.kekikpanel.model.Note;
import com.boraozdogan.kekikpanel.model.User;
import com.boraozdogan.kekikpanel.repository.NoteRepository;
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
		var taskRepo = ctx.getBean(NoteRepository.class);


		var admin = new User("admin", "admin", true);
		var cansimit = new User("cansimit", "1234", false);
		var bora = new User("bora", "1234", false);
		userRepo.save(admin);
		userRepo.save(cansimit);
		userRepo.save(bora);

		taskRepo.save(new Note(cansimit,"Do this", LocalDate.now()));
		taskRepo.save(new Note(admin, "Everything", LocalDate.now()));
        taskRepo.save(new Note(cansimit, "Do that", LocalDate.of(2022, 05, 27)));
		taskRepo.save(new Note(bora, "My first task", LocalDate.of(2022, 05, 26)));
		taskRepo.save(new Note(bora, "My second task", LocalDate.of(2022, 05, 27)));
	}
}
