package net.javaguides.springboot;

import net.javaguides.springboot.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
//		User user = new User();
//		user.setFirstName("Trang");
//		user.setLastName("Nguyen");
//		user.setEmailId("trang@gmail.com");
//		employeeRepository.save(user);
//
//		User user1 = new User();
//		user1.setFirstName("Diep");
//		user1.setLastName("Dao");
//		user1.setEmailId("diep@gmail.com");
//		employeeRepository.save(user1);
	}

}
