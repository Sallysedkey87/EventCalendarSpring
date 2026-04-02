package org.example.EventsCalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class EventsCalendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsCalendarApplication.class, args);
	}

}
