# Basic-Crude-App
Overview:-
This is a RESTful API-based Todo List application built with Spring Boot. It includes features like user authentication, role-based access control, task management, and integration with external APIs (e.g., weather API). The application uses MongoDB as the database.

#Setup Instructions:-
##Prerequisites:-

->   Ensure you have the following installed on your system:
->   Java 17 or higher  
->   Maven 3.6 or higher
->   MongoDB (local or cloud instance like MongoDB Atlas)
->   IDE (IntelliJ IDEA or Eclipse recommended)
->   Configure your JAVA_HOME environment variable.


#Configure Application Properties

Edit the application.properties file located in src/main/resources/ to match your MongoDB setup. 
Example:  spring.data.mongodb.uri=mongodb://localhost:27017/todo_db
          spring.profiles.active=dev

#Use a tool like Postman to test the API endpoints.


#Design Decisions
->Authentication:
  Implemented username/password authentication with role-based access control to secure endpoints.
->MongoDB:
  Chose MongoDB for its flexibility and scalability, ideal for handling dynamic data like tasks.
->Caching:
  Used @PostConstruct to preload application data at startup for faster data fetching.
->Weather API:
  Integrated an external API to fetch weather data for task locations, enhancing the user experience.
->Scheduled Tasks:
  Added JavaMailSender to send reminders using cron jobs.


#System-Specific Dependencies
Cross-OS Compatibility:
Works on Windows, Linux, and macOS.
MongoDB:
Ensure MongoDB is installed and running locally or use a cloud-hosted MongoDB instance.
Java Version:
Requires Java 17 or higher.

