package net.engineeringdigest.journalApp.repositary;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepositary extends MongoRepository<JournalEntity, ObjectId> {

}
