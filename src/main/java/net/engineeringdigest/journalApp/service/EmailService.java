package net.engineeringdigest.journalApp.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class EmailService {
	
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(String to,String subject,String body){
		try{
			SimpleMailMessage mail=new SimpleMailMessage();
			mail.setTo(to);
			mail.setSubject(subject);
			mail.setText(body);
			javaMailSender.send(mail);
		
		}
		catch (Exception e){
			log.error("Exception while sendEmail",e);
		}
	}
	
}
