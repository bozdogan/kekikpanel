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
		userRepo.save(admin);
		userRepo.save(cansimit);

		taskRepo.save(new Note(cansimit, "My first note", LocalDate.of(2022, 05, 27)));
		taskRepo.save(new Note(cansimit, "My second note", LocalDate.of(2022, 05, 28)));
		taskRepo.save(new Note(admin, "Everything", LocalDate.now()));
	}
}
