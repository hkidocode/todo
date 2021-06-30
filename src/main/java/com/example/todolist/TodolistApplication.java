package com.example.todolist;

import com.example.todolist.mysql.repository.TaskRepository;
import com.example.todolist.mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class TodolistApplication extends SpringBootServletInitializer {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		taskRepository.deleteAll();
//	}

	@Scheduled(cron = "@daily")
	void showNumOFNewUsersAfterOneDays() {
		System.out.println(userRepository.count());
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TodolistApplication.class);
	}
}

