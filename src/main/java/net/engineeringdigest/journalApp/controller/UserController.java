package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repositary.UserRepositary;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
	
	    //by autowired the spring will find the match for the object in the beans and if
		//duplicates occurs then it will throw the error
		@Autowired
		private UserService userService;
	
	@Autowired
	private UserRepositary userRepositary;
	
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User userInDB=userService.findByUserName(userName);
				userInDB.setUserName(user.getUserName());
				userInDB.setPassword(user.getPassword());
				userService.saveNewUser(userInDB);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
	}
	@DeleteMapping
	public ResponseEntity<?> deleteUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userRepositary.deleteByUserName(authentication.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
}
