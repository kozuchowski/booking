package com.booking.booking;

import com.booking.booking.model.Facility;
import com.booking.booking.model.Landlord;
import com.booking.booking.repository.FacilityRepository;
import com.booking.booking.repository.LandlordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.Optional;

@SpringBootApplication
public class BookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

	@Bean
	CommandLineRunner  populateDatabase(FacilityRepository fr, LandlordRepository lr) {
		return args -> {
			int count = 1;
			for (int i = 1; i < 11; i++) {
				Landlord landlord = new Landlord("Właściciel" + i, Long.valueOf(i));
				lr.save(landlord);
				for (int j = 0; j < i; j++) {
					fr.save(new Facility("mieszkanko" + count++, 200L + i * 100, 200 + i * 10, "fajne mieszkanko" + i, landlord));
				}
			}

		};
	}


}
