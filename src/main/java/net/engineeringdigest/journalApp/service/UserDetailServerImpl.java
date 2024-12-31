package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repositary.UserRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServerImpl implements UserDetailsService{
	
	@Autowired
	private UserRepositary userRepositary;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepositary.findByUserName(username);
		if(user!=null){
			UserDetails userdetails = org.springframework.security.core.userdetails.User.builder().
					username(user.getUserName())
					.password(user.getPassword())
					.roles(user.getRoles().toArray(new String[0]))
					.build();
			return userdetails;
		}
		throw new UsernameNotFoundException("User not found with username");
	}
}
