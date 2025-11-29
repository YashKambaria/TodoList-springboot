package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
	
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping()
	public ResponseEntity<?> getAllJournalEntriesOfUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		List<JournalEntity> all = user.getJournalEntries();
		if(all!=null && !all.isEmpty()){
			return new ResponseEntity<>(all,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping() //request body helps us to create object from java
	public ResponseEntity<?> createEntries(@RequestBody JournalEntity journalentry){
		// the flow will go like this ...controller --> service --> repository then automated by MongoDB
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		List<JournalEntity> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
		if(!collect.isEmpty()) {
			Optional<JournalEntity> byId = journalEntryService.getById(myId);
			if (byId.isPresent()) {
				return new ResponseEntity<>(byId, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	@DeleteMapping("id/{myId}")
	public ResponseEntity<?> delete(@PathVariable ObjectId myId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Optional<JournalEntity> x = journalEntryService.getById(myId);
			if (x.isPresent()) {
				boolean removed = journalEntryService.delete(myId, userName);
				if(removed){
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
				else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	@PutMapping("id/{id}")
	public ResponseEntity<?> updateJournalById(
			@PathVariable ObjectId id,
			@RequestBody JournalEntity newEntry)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		List<JournalEntity> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
		if(!collect.isEmpty()) {
			JournalEntity old = journalEntryService.getById(id).orElse(null);
			if (old != null) {
				if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
					old.setTitle(newEntry.getTitle());
				}
				if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
					old.setContent(newEntry.getContent());
				}
				
				journalEntryService.saveEntry(old);
				return new ResponseEntity<>(old, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
