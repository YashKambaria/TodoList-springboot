package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
	
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("{userName}")
	public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
		User user = userService.findByUserName(userName);
		List<JournalEntity> all = user.getJournalEntries();
		if(all!=null && !all.isEmpty()){
			return new ResponseEntity<>(all,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("{userName}") //request body helps us to create object from java
	public ResponseEntity<?> createEntries(@RequestBody JournalEntity journalentry,@PathVariable String userName){
		// the flow will go like this ...controller --> service --> repository then automated by MongoDB
		try {
			journalentry.setDate(LocalDateTime.now());
			//now we will use different http codes so that its easy for client to detect the problem
			journalEntryService.saveEntry(journalentry,userName);
			return new ResponseEntity<>(journalentry, HttpStatus.CREATED);
		}
		catch (Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("id/{myId}") // here the format is to store the data and @PathVariable stores that data
	public ResponseEntity<?> getJournalEntryId(@PathVariable ObjectId myId){
		Optional<JournalEntity> byId = journalEntryService.getById(myId);
		if(byId.isPresent()){
			return new ResponseEntity<>(byId,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	@DeleteMapping("id/{userName}/{myId}")
	public ResponseEntity<?> delete(@PathVariable ObjectId myId,@PathVariable String userName){
		Optional<JournalEntity> x=journalEntryService.getById(myId);
		if(x.isPresent()){
			journalEntryService.delete(myId,userName);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	@PutMapping("id/{userName}/{id}")
	public ResponseEntity<?> updateJournalById(
			@PathVariable ObjectId id,
			@RequestBody JournalEntity newEntry,
			@PathVariable String userName)
	{
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
