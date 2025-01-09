package net.engineeringdigest.journalApp.scheduler;


import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repositary.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AppCache appCache;
	
	@Scheduled(cron = "*/10 * * * * *")
	public void clear_cache() {
		appCache.init();
	}
	
	
//	@Scheduled(cron = "*/10 * * * * *")
//	public void sendMail(){
//		emailService.sendEmail("23bce024@nirmauni.ac.in","hello","its every 10 second scheduled mail");
//	}
}
