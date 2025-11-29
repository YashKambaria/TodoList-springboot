package net.engineeringdigest.journalApp.scheduler;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.model.SentimentData;
import net.engineeringdigest.journalApp.repositary.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserScheduler {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AppCache appCache;
	
	@Autowired
	private UserRepositoryImpl userRepository;
	
	@Autowired
	private KafkaTemplate<String,SentimentData> kafkaTemplate;
	
	
	@Scheduled(cron = "5 * * * * *")
	public void fetchUsersAndSendSaMail(){
		List<User> users =userRepository.getUserForSA();
		for(User user:users){
			List<JournalEntity> journalEntries = user.getJournalEntries();
			List<Sentiment> sentiments = journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7,ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
			
			Map<Sentiment,Integer> sentimentCount=new HashMap<>();
			for(Sentiment sentiment:sentiments){
				if(sentiment!=null){
					sentimentCount.put(sentiment,sentimentCount.getOrDefault(sentiment,0)+1);
				}
			}
			Sentiment mostFrequent=null;
			int maxCount=0;
			
			for(Map.Entry<Sentiment, Integer> entry:sentimentCount.entrySet()){
				if(entry.getValue()>maxCount){
					maxCount=entry.getValue();
					mostFrequent=entry.getKey();
				}
			}
			if(mostFrequent!=null){
				SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequent).build();
				try {
					kafkaTemplate.send("yash", sentimentData.getEmail(), sentimentData)
							.addCallback(
									success -> log.info("Message sent successfully: {}", sentimentData),
									failure -> log.error("Failed to send message", failure)
							);
					
				}
				catch (Exception e){
					//fallback for kafka now the data will go synchronously
					log.info("Kafka expired or error in Kafka");
					emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
				}
			}
		}
	}
	
	@Scheduled(cron = "*/10 * * * * *")
	public void clear_cache() {
		appCache.init();
	}
}
