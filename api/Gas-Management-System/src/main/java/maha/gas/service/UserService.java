package maha.gas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;
import maha.gas.entity.Users;
import maha.gas.repository.UserRepository;

@Service
public class UserService {
	
	Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserRepository userRepository;
	
	public Users registerUser(Users user) {
		Users save = userRepository.save(user);
		logger.info("Registration Successful");
		return save;
	}
	
	public List<Users> getAllUsers(){
		List<Users> users = new ArrayList<Users>();
		userRepository.findAll().forEach(user -> users.add(user));
	      return users; 
	}
	
	public Users findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public void deleteUser(int id) {
	    Optional<Users> findById = userRepository.findById(id);
	    if(findById.isPresent()) { // Use .isPresent() to check if Optional contains a value
	        userRepository.deleteById(id);
	        logger.info("User Deleted Successfully"); // Corrected typo in log message
	    } else {
	        logger.info("Invalid User ID");
	    }
	}
	
	public Users login(String email, String password) {
		Users findByEmail = userRepository.findByEmail(email);
		if(findByEmail != null) {			
			Users exist = userRepository.findByEmailAndPassword(email, password);
			return exist;
		}else {
			logger.error("User Not Found");
			return null;
		}
	}
	

}
