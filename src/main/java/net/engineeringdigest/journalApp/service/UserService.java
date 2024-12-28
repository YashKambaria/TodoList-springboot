package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repositary.JournalEntryRepositary;
import net.engineeringdigest.journalApp.repositary.UserRepositary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	
	
	public void saveEntry(User userEntity){
		userRepositary.save(userEntity);
	}
	public List<User> getAll(){
		return userRepositary.findAll();
	}
	public Optional<User> getById(ObjectId id){
		return userRepositary.findById(id);
	}
	public void delete(ObjectId id){
		userRepositary.deleteById(id);
	}
	public User findByUserName(String userName){
		return userRepositary.findByUserName(userName);
	}
}
