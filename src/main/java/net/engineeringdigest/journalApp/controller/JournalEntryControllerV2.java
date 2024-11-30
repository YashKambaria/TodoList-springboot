package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
	
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		List<JournalEntity> all = journalEntryService.getAll();
		if(all!=null && !all.isEmpty()){
			return new ResponseEntity<>(all,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping //request body helps us to create object from java
	public ResponseEntity<?> createEntries(@RequestBody JournalEntity journalentry){
		// the flow will go like this ...controller --> service --> repository then automated by MongoDB
		journalentry.setDate(LocalDateTime.now());
		//now we will use different http codes so that its easy for client to detect the problem
		journalEntryService.saveEntry(journalentry);
		return new ResponseEntity<>(journalentry,HttpStatus.CREATED);
	}
	@GetMapping("id/{myId}") // here the format is to store the data and @PathVariable stores that data
	public ResponseEntity<?> getJournalEntryId(@PathVariable ObjectId myId){
		Optional<JournalEntity> byId = journalEntryService.getById(myId);
		if(byId.isPresent()){
			return new ResponseEntity<>(byId,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	@DeleteMapping("id/{myId}")
	public ResponseEntity<?> delete(@PathVariable ObjectId myId){
		Optional<JournalEntity> x=journalEntryService.getById(myId);
		if(x.isPresent()){
			journalEntryService.delete(myId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	@PutMapping("/id/{id}")
	public ResponseEntity<?> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntity newEntry){
		JournalEntity old=journalEntryService.getById(id).orElse(null);
		if(old!=null) {
			old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
			old.setContent(newEntry.getContent()!=null  && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
			journalEntryService.saveEntry(old);
			return new ResponseEntity<>(old,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
