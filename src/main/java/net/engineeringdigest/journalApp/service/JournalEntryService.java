package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repositary.JournalEntryRepositary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
	
	@Autowired
	private JournalEntryRepositary journalEntryRepositary;
	
	@Autowired
	private UserService userService;
	
	
	//here Transactional is the feature which will help us when we face an error in storing for example.......we have one entry to store in user and journal entry now suppose some error occur after we had saved entry in journal entity so now Transactions will rollback and undo the operation.....this is not done manually we have to implement somethings in main class also
	@Transactional
	public void saveEntry(JournalEntity journalEntity, String userName){
		try {
			User user = userService.findByUserName(userName);
			journalEntity.setDate(LocalDateTime.now());
			JournalEntity saved = journalEntryRepositary.save(journalEntity);
			user.getJournalEntries().add(saved);
			user.setUserName(null);
			userService.saveEntry(user);
		}
		catch (Exception e){
			throw new RuntimeException("An error occurr while storing the entry"+e);
		}
		
	}
	public void saveEntry(JournalEntity journalEntity){
		journalEntryRepositary.save(journalEntity);
	}
	public List<JournalEntity> getAll(){
		return journalEntryRepositary.findAll();
	}
	public Optional<JournalEntity> getById(ObjectId id){
		return journalEntryRepositary.findById(id);
	}
	public void delete(ObjectId id, String userName){
		User user = userService.findByUserName(userName);
		user.getJournalEntries().removeIf(x->x.getId().equals(id));
		userService.saveEntry(user);
		journalEntryRepositary.deleteById(id);
	}
}
