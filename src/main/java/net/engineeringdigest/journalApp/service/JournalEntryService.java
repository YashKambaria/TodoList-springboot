package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.repositary.JournalEntryRepositary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
	
	@Autowired
	private JournalEntryRepositary journalEntryRepositary;
	
	
	public void saveEntry(JournalEntity journalEntity){
		journalEntryRepositary.save(journalEntity);
	}
	public List<JournalEntity> getAll(){
		return journalEntryRepositary.findAll();
	}
	public Optional<JournalEntity> getById(ObjectId id){
		return journalEntryRepositary.findById(id);
	}
	public void delete(ObjectId id){
		journalEntryRepositary.deleteById(id);
	}
}
