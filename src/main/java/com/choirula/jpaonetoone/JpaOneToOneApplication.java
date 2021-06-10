package com.choirula.jpaonetoone;

import com.choirula.jpaonetoone.model.Gender;
import com.choirula.jpaonetoone.model.User;
import com.choirula.jpaonetoone.model.UserProfile;
import com.choirula.jpaonetoone.repository.UserProfileRepository;
import com.choirula.jpaonetoone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;

@SpringBootApplication
public class JpaOneToOneApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpaOneToOneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Clean up database tables
		userProfileRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();

		// Create a User instance
		User user = new User("Choirul", "Andriansyah", "choirul@gmail.com", "choi1234");

		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.set(1998,9,21);

		UserProfile userProfile = new UserProfile("+62 82234282989", Gender.MALE, dateOfBirth.getTime(),
				"774", "Block-M", "Jalan Alamanda", "Bantul",
				"Yogyakarta", "Indonesia", "63351");

		// Set child reference(userProfile) in parent entity(user)
		user.setUserProfile(userProfile);

		// Set parent reference(user) in child entity(userProfile)
		userProfile.setUser(user);

		// Save Parent Reference (which will save the child as well)
		userRepository.save(user);
	}
}
