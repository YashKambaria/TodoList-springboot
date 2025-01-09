package net.engineeringdigest.journalApp.servicetest;


import net.engineeringdigest.journalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailMailSenderTest {
	
	
	@Autowired
	private EmailService emailService;
	
	@Test
	public void sendTest(){
		emailService.sendEmail("23bce160@nirmauni.ac.in","Hello its mee","I am sending mail through Spring boot");
		emailService.sendEmail("23bce179@nirmauni.ac.in","Hello its mee","I am sending mail through Spring boot");
	}
}
