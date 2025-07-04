package com.example.streak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.time.ZoneId;
import java.util.TimeZone;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class StreakApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Seoul")));
		SpringApplication.run(StreakApplication.class, args);
	}

}
