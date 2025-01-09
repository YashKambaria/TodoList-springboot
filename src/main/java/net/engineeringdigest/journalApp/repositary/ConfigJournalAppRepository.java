package net.engineeringdigest.journalApp.repositary;

import net.engineeringdigest.journalApp.entity.ConfigJournalApp;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalApp, ObjectId> {

}
