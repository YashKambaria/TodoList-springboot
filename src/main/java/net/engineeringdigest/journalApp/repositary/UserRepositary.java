package net.engineeringdigest.journalApp.repositary;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepositary extends MongoRepository<User, ObjectId> {
	User findByUserName(String username);
}
