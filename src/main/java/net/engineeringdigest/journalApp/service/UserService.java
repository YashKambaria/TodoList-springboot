package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repositary.JournalEntryRepositary;
import net.engineeringdigest.journalApp.repositary.UserRepositary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


//here component means it stores the class in beans or imagine it like a bag in which stores the items and
//using Autowired the spring will search for the item of the given name and we can use it

//Dependency injection is the process by which Spring looks up the beans that are needed for
// particular bean to function and injects them as a dependency. Spring can perform dependency injection
// by using constructor or by using a setter method.

@Component
public class UserService {
	
	@Autowired
	private UserRepositary userRepositary;
	
	private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
	public void saveEntry(User user){
		userRepositary.save(user);
	}
	public void saveNewUser(User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("User"));
		userRepositary.save(user);
	}
	public List<User> getAll(){
		return userRepositary.findAll();
	}
	public Optional<User> getById(ObjectId id){
		return userRepositary.findById(id);
	}
	public void deleteById(ObjectId id){
		userRepositary.deleteById(id);
	}
	public User findByUserName(String userName){
		return userRepositary.findByUserName(userName);
	}
	
	public void saveAdmin(User user) {
		User byUserName = userRepositary.findByUserName(user.getUserName());
		byUserName.setRoles(Arrays.asList("User","ADMIN"));
		userRepositary.save(byUserName);
	}
}
