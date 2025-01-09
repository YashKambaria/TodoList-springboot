package net.engineeringdigest.journalApp.cache;

import net.engineeringdigest.journalApp.entity.ConfigJournalApp;
import net.engineeringdigest.journalApp.repositary.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class AppCache {
	
	@Autowired
	private ConfigJournalAppRepository configJournalAppRepository;
	
	
	public Map<String,String> APP_CACHE;
	
	
	@PostConstruct
	public void init(){
		APP_CACHE=new HashMap<>();
		List<ConfigJournalApp> all = configJournalAppRepository.findAll();
		for(ConfigJournalApp a:all) {
			APP_CACHE.put(a.getKey(), a.getValue());
		}
	}
}
