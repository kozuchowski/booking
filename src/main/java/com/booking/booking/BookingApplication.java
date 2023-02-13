package com.booking.booking;

import com.booking.booking.model.Facility;
import com.booking.booking.model.Landlord;
import com.booking.booking.model.Reservation;
import com.booking.booking.model.Tenant;
import com.booking.booking.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
public class BookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

	@Bean
	CommandLineRunner  populateDatabase(FacilityRepository fr, LandlordRepository lr, TenantRepository tr, ReservationRepository rr) {
		return args -> {
			int count = 1;
			for (int i = 1; i < 5; i++) {
				Landlord landlord = new Landlord("Właściciel" + i, Long.valueOf(i));

				int random = (int) ((Math.random() * (3)) + 1);
				lr.save(landlord);
				for (int j = 0; j < random; j++) {
					Tenant tenant = new Tenant("Tenant" + j+i);
					Facility facility = new Facility("mieszkanko" + count++, 200L + i * 100,
							200 + i * 10, "fajne mieszkanko" + count, landlord);
					tr.save(tenant);
					fr.save(facility);
					Tenant t = tr.findByTenantName(tenant.getName());
					Facility f = fr.findByName(facility.getName());
					rr.save(new Reservation(LocalDateTime.now().minusDays(i), LocalDateTime.now().plusDays(i), f, t));
				}
			}

		};
	}


}
