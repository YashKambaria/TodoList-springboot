package net.engineeringdigest.journalApp.servicetest;

import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.Assertions;
import net.engineeringdigest.journalApp.repositary.UserRepositary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {
	
	@Autowired
	private UserRepositary userRepositary;
	
	@Test
	public void findByUserName(){
		assertNotNull(userRepositary.findByUserName("Ram"));
	}
	
}
