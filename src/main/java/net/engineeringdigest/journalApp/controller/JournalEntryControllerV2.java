package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
	
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	
	@GetMapping
	public List<JournalEntity> getAll(){
		return journalEntryService.getAll();
	}
	
	@PostMapping //request body helps us to create object from java
	public JournalEntity createEntries(@RequestBody JournalEntity journalentry){
		// the flow will go like this ...controller --> service --> repository then automated by MongoDB
		journalentry.setDate(LocalDateTime.now());
		journalEntryService.saveEntry(journalentry);
		return journalentry;
	}
	@GetMapping("id/{myId}") // here the format is to store the data and @PathVariable stores that data
	public JournalEntity getJournalEntryId(@PathVariable ObjectId myId){
		return journalEntryService.getById(myId).orElse(null);
	}
	@DeleteMapping("id/{myId}")
	public boolean delete(@PathVariable ObjectId myId){
		journalEntryService.delete(myId);
		return true;
	}
	@PutMapping("/id/{id}")
	public JournalEntity updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntity newEntry){
		JournalEntity old=journalEntryService.getById(id).orElse(null);
		if(old!=null) {
			old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
			old.setContent(newEntry.getContent()!=null  && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
		}
		journalEntryService.saveEntry(old);
		return old;
	}
}
