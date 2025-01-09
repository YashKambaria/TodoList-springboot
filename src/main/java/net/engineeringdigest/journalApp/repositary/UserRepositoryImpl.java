package net.engineeringdigest.journalApp.repositary;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl {
	
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<User> getUserForSA(){
		Query query=new Query();
//		query.addCriteria(Criteria.where("email").exists(true));
//		query.addCriteria(Criteria.where("email").ne("").ne(null));
		
		//this is regular expression for email
		query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"));
		
		query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
		List<User> users = mongoTemplate.find(query, User.class);
		return users;
	}
}
