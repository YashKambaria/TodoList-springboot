package net.engineeringdigest.journalApp.Repositary;


import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repositary.UserRepositoryImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepositoryImplTest {
	
	@Autowired
	private UserRepositoryImpl userRepository;
	
	@Disabled
	@Test
	public void get(){
		userRepository.getUserForSA();
	}
}
