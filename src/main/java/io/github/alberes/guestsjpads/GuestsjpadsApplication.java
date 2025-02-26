package io.github.alberes.guestsjpads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GuestsjpadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestsjpadsApplication.class, args);
	}

}
