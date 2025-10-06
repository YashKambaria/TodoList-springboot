package net.engineeringdigest.journalApp.entity;

import lombok.*;
import net.engineeringdigest.journalApp.enums.Sentiment;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Document(collection = "journal_Entries") //it is mainly used to map it with the MongoDB and it is Based on NoSQL
public class JournalEntity {
	//entity basically means the variables that has been used by the concept of encapsulation.
	@Id  //it will specifically map the id with the MongoDB
	private ObjectId id;
	private String name;
	
	private LocalDateTime date;
	@NonNull
	private String title;
	private String content;
	private Sentiment sentiment;
}
