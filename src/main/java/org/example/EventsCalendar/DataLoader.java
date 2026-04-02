package org.example.EventsCalendar;

import org.example.EventsCalendar.models.Event;
import org.example.EventsCalendar.repositories.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(EventRepository repository) {
        return args -> {
            // Add some dummy data dynamically based on current month so it shows up
            LocalDate now = LocalDate.now();
            repository.save(new Event("Spring Boot Workshop", now.withDayOfMonth(10).toString()));
            repository.save(new Event("Team Meeting", now.withDayOfMonth(15).toString()));
            repository.save(new Event("Project Deadline", now.withDayOfMonth(22).toString()));
        };
    }
}
