package com.boraozdogan.kekikpanel;

import com.boraozdogan.kekikpanel.model.Note;
import com.boraozdogan.kekikpanel.model.User;
import com.boraozdogan.kekikpanel.repository.NoteRepository;
import com.boraozdogan.kekikpanel.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;


@SpringBootApplication
public class KekikPanelApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx =
				SpringApplication.run(KekikPanelApplication.class, args);

		// NOTE(bora): Initialize test data
		UserRepository userRepo = ctx.getBean(UserRepository.class);
		NoteRepository taskRepo = ctx.getBean(NoteRepository.class);

		User admin = new User("admin", "admin", true);
		User cansimit = new User("cansimit", "1234", false);
		userRepo.save(admin);
		userRepo.save(cansimit);

		taskRepo.save(new Note(cansimit, "My first note", LocalDate.of(2022, 05, 27)));
		taskRepo.save(new Note(cansimit, "My second note", LocalDate.of(2022, 05, 28)));
		taskRepo.save(new Note(admin, "Everything", LocalDate.now()));
	}
}
