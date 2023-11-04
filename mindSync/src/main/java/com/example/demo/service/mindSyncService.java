package com.example.demo.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.demo.entity.userInfoAudit;
import com.example.demo.interfaces.mindSyncMongoInterface;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
@NoArgsConstructor
//@Service
public class mindSyncService implements mindSyncMongoInterface {
	
	@Autowired 
	UserRepository UserRepository;
	
	@Autowired
	public JavaMailSender mailSender;
	
	@Autowired
	public HttpServletRequest request;

	
//	@Autowired 
//	userInfoAudit userInfoAud;
//
//
//	public mindSyncService(userInfoAudit userInfoAud) {
//		this.userInfoAud=userInfoAud;
////		this.UserRepository=UserRepository;

//	}

	//signup
	@Override
	public void saveUserInfo(String email, String userName, String password) {
		userInfoAudit userInfoAud = new userInfoAudit();
		userInfoAud.setEmail(email);
		userInfoAud.setUserName(userName);
		userInfoAud.setPassword(password);
		UserRepository.save(userInfoAud);

		return;
	}
	
	
	@Override
	public String getOneUser(String email, String userName, String password, String funName, String newPassword) {

	    List<userInfoAudit> userInfoAud = (List<userInfoAudit>) UserRepository.findByEmail(email);

	    // SignUp condition
	    if (userInfoAud.isEmpty() && funName.equals("signUp")) {
	        return "NewUser";
	    } 	    
		    else if (userInfoAud != null && funName.equals("login")) {
		        // Login Condition or ForgotPassword Condition       	        	
		        for (userInfoAudit user : userInfoAud) {     	
		        	
		            if (user.getEmail().equals(email) && user.getPassword().equals(password) && user.getUserName().equals(userName)) {
		                       	
		            	return "Matched";
		            }
		        }
		        return "Unmatched"; // This line is executed if no user was matched in the loop.
		    } 
	    
		    else if (userInfoAud != null && funName.equals("updatePassword")) {		
	    	for (userInfoAudit user : userInfoAud) {
	    		user.setPassword(newPassword);
	    	}
	    	return "Updated";

		    }
	    
		    
	    
	    else {
	    	for (userInfoAudit user : userInfoAud) {
	        	
	        	HttpSession session = request.getSession();
	        	session.setAttribute("userName", user.getUserName());}
	        return "Existing";
	    }
	}
	
	//forgotPassword
	 public String sendEmail(String mail) throws Exception {
		 try {
			 
			 Random random = new Random();
		     int otp = 100000 + random.nextInt(900000);
		     String body = "Hi" + ", \n" + " HERE'S SIX DIGIT OTP " + otp + "\n Please don't share with anyone";
		     
		     SimpleMailMessage message = new SimpleMailMessage();
		     String subject = "OTP FOR RESET PASSWORD";
		     message.setFrom("workwebsite2000@gmail.com");
		     message.setTo(mail);
		     message.setText(body);
		     message.setSubject(subject);
		     mailSender.send(message);
		     return  String.valueOf(otp);

		    } catch (Exception e) {			      
		    	 e.printStackTrace(); 
			     return "Not Sent";

		    }

	 }



	@Override
	public List<userInfoAudit> getOneUser(String email) {
		// TODO Auto-generated method stub
	    List<userInfoAudit> userInfoAud = (List<userInfoAudit>) UserRepository.findByEmail(email);
		return userInfoAud;
		
	}


	@Override
	public String updateDetail(String email, String fullname, String password) {
		// TODO Auto-generated method stub
	    List<userInfoAudit> userInfoAud = (List<userInfoAudit>) UserRepository.findByEmail(email);
	    for(userInfoAudit aud: userInfoAud) {
	    	aud.setUserName(fullname);
	    	aud.setPassword(password);
			UserRepository.save(aud);

	    }
		return "Success";
	}

	


	
	

		
		
		
	}

		//UserRepository.findOne(email); no findOne method in mongorepo
		
		
	
	
	



	