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
@RequestMapping("/user")
public class UserController {
	
	    //by autowired the spring will find the match for the object in the beans and if
		//duplicates occurs then it will throw the error
		@Autowired
		private UserService userService;
		
		@GetMapping
		public List<User> getAll(){
			return userService.getAll();
		}
	
	@PostMapping
	public void createEntries(@RequestBody User user){
		userService.saveEntry(user);
	}
	@PutMapping("/{username}")
	public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String username){
			User userInDB=userService.findByUserName(username);
			if(userInDB!=null){
				userInDB.setUserName(user.getUserName());
				userInDB.setPassword(user.getPassword());
				userService.saveEntry(userInDB);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
	}
}
