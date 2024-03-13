package maha.gas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import maha.gas.entity.Users;
import maha.gas.exception.GlobalException;
import maha.gas.mailConfig.EmailSenderService;
import maha.gas.service.UserService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {
		
	@Autowired
	UserService userService;
	
	@Autowired EmailSenderService emailSender;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody Users user) {
		String tempEmail = user.getEmail();
		String subject = "Thankyou For Registration";
		String body = "Heyy Bhava";
		Users registerUser = userService.registerUser(user);
		
		emailSender.sendEmail(tempEmail,subject,body);
		return new ResponseEntity<>(registerUser, HttpStatus.OK);
	}
	
	@GetMapping("/getAllUser")
	public List<Users> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/emailExist/{email}")
	public Users emailAlreadyExist(@PathVariable String email) {
		Users emailCheck = userService.findUserByEmail(email);
		if(emailCheck != null) {
			System.out.println(emailCheck);
		}else {
			try {				
				throw new GlobalException("Email id does not exist");
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return emailCheck;
	}
	
//	@GetMapping("/getUserByEmail")
//	public Users getUserByEmail(@PathVariable String email) {
//		
//	}
	
	@DeleteMapping("/deleteUserById/{id}")
	public void deleteById(@PathVariable int id) {
		userService.deleteUser(id);
	}
	
	@PostMapping("/login")
	public Users login(@RequestBody Users user) {
		String tempEmail = user.getEmail();
		String tempPassword = user.getPassword();
		Users login = userService.login(tempEmail, tempPassword);
		return login;
	}
	
	@PostMapping("/byProduct")
	public String byProduct(@RequestParam String user) {
		String email = user;
		String subject = "Thankyou For Registration";
		String body = "Heyy Bhava";		
		emailSender.sendEmail(email,subject,body);
		return "Order Booked";
	}


}
