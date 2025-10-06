package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.utilis.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class publicController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/sign-up")
	public void signup(@RequestBody User user){
		userService.saveNewUser(user);
	}
	
	@PostMapping("/login")
	public  ResponseEntity<String> login(@RequestBody User user) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
			UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
			String jwt=jwtUtil.generateToken(userDetails.getUsername());
			return new ResponseEntity<>(jwt, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occur while create AuthentcationToken ",e);
			return new ResponseEntity<>("Incorrect username of password",HttpStatus.BAD_REQUEST);
		}
	}
}
