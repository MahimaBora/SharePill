package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.userInfoAudit;
import com.example.demo.entity.userPost;
import com.example.demo.interfaces.mindSyncMongoInterface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
//@RequestMapping(value="/mindSync")
public class mindSyncController {
	
//	@GetMapping(value="/createPost")
//	ResponseEntity<String> setBlog(){		
//		return ResponseEntity.status(HttpStatus.OK).body("Got It");
//	}
	
	
	
	@Autowired
	public mindSyncMongoInterface mongoInt;	
		
	public mindSyncController(mindSyncMongoInterface mongoInt) {
		this.mongoInt=mongoInt;
	}
	
	@Autowired
	public RestTemplate restTemplate;	
	
	

	
	@RequestMapping("/joinUS")
	public String joinUs(Model model, HttpServletRequest request) {	
	    HttpSession session = request.getSession();
		if (session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn")) {
			model.addAttribute("msg",session.getAttribute("userName").toString());		
//	        return "redirect:/mindSync/";
	        return "redirect:/";


		}else {
			return "loginPage";

		}		
	}

	
	
//	@ResponseBody
	@PostMapping(value = "/loginsubmit")
	public String loginInfo(Model model, @RequestParam(name = "email")String email, @RequestParam String userName, String password, HttpServletRequest request) {
		
		if(email.isEmpty() && userName.isEmpty()&& password.isEmpty()) {
			model.addAttribute("msg","Incorrect Details");
			return "loginPage";
		}
		else {			
			String str= mongoInt.getOneUser(email,userName, password,"login",null);
			if(str.equals("Matched")) {
				
			    HttpSession session = request.getSession();
			    session.setAttribute("email", email);
			    session.setAttribute("userName", userName);
			    session.setAttribute("loggedIn", true);

				model.addAttribute("msg",session.getAttribute("userName").toString());		


//		        return "redirect:/mindSync/";
		        return "redirect:/";

			}
			else {
				model.addAttribute("msg","Incorrect Details");
				return "loginPage";
			}
		}			
	}

	@PostMapping(value = "/signupsubmit")
	public String SignUpInfo(Model model, String email, String userName, String password, HttpServletRequest request) {		

		if(email.isEmpty() && userName.isEmpty()&& password.isEmpty()) {
			model.addAttribute("msg","Incorrect Details");
			return "loginPage";
		}
		else {			
			String str= mongoInt.getOneUser(email,userName, password,"signUp",null);

			if(str.equals("NewUser")) {
				mongoInt.saveUserInfo(email,userName,password);
				HttpSession session = request.getSession();
				model.addAttribute("msg","Pls Login with your ID");	

				return "loginPage";
			}
			else {
				model.addAttribute("msg","EmailId Already Used");
				return "loginPage";
			}
		}			
	}
	
/////////////////////////////////////////////////////////////////////////////////	
	
	@GetMapping(value = "/forgotPassword")
	public String forgotPwdPage(Model model) {
		
		return "loginpage";
	}

	
	@PostMapping(value = "/EmailOTP")
	public String forgotPassword(Model model, String email, HttpServletRequest request) throws Exception {
		
		String verifiedEmail = mongoInt.getOneUser(email,null,null,"signUp",null);
		
		if(verifiedEmail.equals("Existing")) {	
			HttpSession session = request.getSession();	
			session.setAttribute("email",email);	

			String str = mongoInt.sendEmail(email);
			
				if(str.equals("Not Sent")) {	
					model.addAttribute("headingUserName","Hi "+session.getAttribute("userName"));		
					model.addAttribute("msg","Unknown Error, Please Contact Helpline");		
				}
				else {
					session.setAttribute("otpTimestamp",System.currentTimeMillis());
					session.setAttribute("otp",str);	
					model.addAttribute("headingUserName","Hi "+session.getAttribute("userName"));		
					model.addAttribute("msg","OTP sent via mail");		
				}
		}
		else {
			model.addAttribute("msg","Incorrect Mail Id");	
		}
				
	return "loginPage";
		
	}
	
	
	 @PostMapping(value = "/verifyOTP")
	  public String verifyOTP(Model model, String otp,HttpServletRequest request) {
		  HttpSession session= request.getSession();
	      if (session.getAttribute("otp").equals(otp)) {
//	      if ("12345".equals(otp)) {

	    	  long currentTime = System.currentTimeMillis();
	          	if (currentTime - (long)session.getAttribute("otpTimestamp") <= 30000) {
					model.addAttribute("headingUserName","Hi "+session.getAttribute("userName"));		
	          		model.addAttribute("msg", "Successfully VERIFIED");
//	 		         return "loginPage";
	          	}
		       else{
					model.addAttribute("headingUserName","Hi "+session.getAttribute("userName"));
		    	    model.addAttribute("msg", "OTP Expired");
//			       return "loginPage";
		       }
	      }	
	      else {
				model.addAttribute("headingUserName","Hi "+session.getAttribute("userName"));
	            model.addAttribute("msg", "Incorrect OTP");
//	          return "loginPage";
	      }	 
	       return "loginPage";

	  }
	 

	 @PostMapping(value = "/updatePassword")
		public String resetPassword(Model model, String newPassword, String rePassword, HttpServletRequest request) throws Exception {
			HttpSession session = request.getSession();	
			if(newPassword.equals(rePassword)) {
				String str= mongoInt.getOneUser(session.getAttribute("email").toString(),null, newPassword,"updatePassword",null);
				if(str.equals("Updated")) {
				model.addAttribute("headingUserName",session.getAttribute("userName"));
				model.addAttribute("msg","Password Updated!, Login with Updated Password");
		        
				}

			}
			
			else {
				model.addAttribute("headingUserName",session.getAttribute("userName"));
				model.addAttribute("msg","Password Didn't Match");

			}			
			return "loginPage";

		}
	 
	 @RequestMapping(value = "/generatePost")
	    public String getpostPage(HttpServletRequest request) {
	        HttpSession session = request.getSession();
	        if (session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn")) {
	            String email = session.getAttribute("email").toString();
	            String userName = session.getAttribute("userName").toString();
//	            String url = "http://localhost:8082/mindSync/createPost" + "?email=" + email + "&userName=" + userName;
	            String url = "https://Post-Service.azurewebsites.net/createPost" + "?email=" + email + "&userName=" + userName;

	            // Handle your redirection logic here
	            return "redirect:" + url; // Redirect the user to the specified URL
	        } else {
	            return "redirect:/joinUS"; // Redirect to the joinUS URL
	        }
	    }
//	 @ResponseBody
//	 @GetMapping(value="/getUserInfo")
//		 public List<userInfoAudit> getUserInfo() {
//			 List<userInfoAudit> userInfo = mongoInt.getAllUsers();
//			 return  userInfo;
//		 }
	 
		 
//		 @RequestMapping(value="/generatePost")
//		 public ResponseEntity<String> getpostPage(HttpServletRequest request) {
//			 
//			 HttpSession session = request.getSession();
//			 if (session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn")) {
//			 String email= session.getAttribute("email").toString();
//			 String userName= session.getAttribute("userName").toString();
//				System.out.println("dvrfrfgre"+userName);
//			 String url = "http://localhost:8082/mindSync/createPost"+ "?email=" + email+ "&userName="+userName;
//				System.out.println("dvrf"+url);
//
//		        return restTemplate.getForEntity(url, String.class);
//			 }
//
//		 }
		 
		 
//		 @ResponseBody
//		 @GetMapping(value="/getUserInfo")
//			 public List<userInfoAudit> getUserInfo() {
//				 List<userInfoAudit> userInfo = mongoInt.getOneUserInfo();
//				 return  userInfo;
//			 }
	
	
		 
		 @RequestMapping("/")
			public String showAllPost(HttpServletRequest request, Model model) {
			 


				userPost[] list= restTemplate.getForObject("http://Post-Service/showPost", userPost[].class);
				model.addAttribute("list",list);

			 	HttpSession session = request.getSession();
			 	
			    if (session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn") ) {

					model.addAttribute("msg",session.getAttribute("userName").toString());		
					userPost[] list1= restTemplate.getForObject("http://Post-Service/showPost", userPost[].class);
					model.addAttribute("list",list1);
			 	}

				

				return "feed.html";
			}
		 
		 
		 
		 
		
			@RequestMapping("/rating")
			public String rating(Model model, Integer postId) {
				try {
					if(postId!=null) {
						restTemplate.getForObject("http://Post-Service/rate"+"?postId="+postId,Integer.class);	
				        return "redirect:/";
					}
				}			
				catch(Exception e) {			
					e.printStackTrace();			

				}			
				return "feed.html";
			}
			
			@RequestMapping("/aboutUs")
			public String aboutUs(Model model, HttpServletRequest request) {
HttpSession session = request.getSession();
			 	
			    if (session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn")) {
					model.addAttribute("msg",session.getAttribute("userName").toString());		

			    }
				return "aboutUs.html";
			}

			//userSetting
			@RequestMapping("/personalSpace")
			public String personalSpace(Model model, HttpServletRequest request) {
				 
				HttpSession session = request.getSession();
			 	
			    if (session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn")) {
					String email= session.getAttribute("email").toString();
				 String userName= session.getAttribute("userName").toString();
				 List<userInfoAudit> data= mongoInt.getOneUser(email);
				model.addAttribute("data",data);
				model.addAttribute("msg",userName);

				return "personalSpace.html";

			    }
			    else {
			        return "redirect:/joinUS";
			    }

			}			
			

			@RequestMapping("/updateDetails")
			public String updateDetails(RedirectAttributes RedirectAttributes,HttpServletRequest request, Model model, String fullname, String password) {
				 HttpSession session = request.getSession();
				    if (session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn")) {

				 String email= session.getAttribute("email").toString();
				String success=mongoInt.updateDetail(email,fullname,password);
				
				if(success.equals("Success")) {
//					model.addAttribute("updateResult","Saved");
				      RedirectAttributes.addFlashAttribute("updateResult", "Saved");


				}
		        return "redirect:/personalSpace";

				    }
				    else {
				        return "redirect:/joinUS";

				    }

			}
			
			@RequestMapping("/getuserPost")
			public String getuserPost(HttpServletRequest request, Model model) {
				HttpSession session = request.getSession();

				if (session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn")) {
					String userName= session.getAttribute("userName").toString();
					String email= session.getAttribute("email").toString();

					userPost[] list= restTemplate.getForObject("http://Post-Service/showOnePost"+"?email="+email, userPost[].class);
					model.addAttribute("list",list);
					model.addAttribute("msg",userName);
					return "myPost.html";

				}
				else {
				    return "redirect:/joinUS";

				}

			}
			
			@RequestMapping("/deletePost")
			public String deletePost(HttpServletRequest request, Model model, Integer postId) {
				HttpSession session = request.getSession();

				if (session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn")) {
					
					restTemplate.getForObject("http://Post-Service/deleteUserPost"+"?postId="+postId,Integer.class);	
					
				    return "redirect:/getuserPost";

				}
				else {
				    return "redirect:/getuserPost";

				}

			}


			  @RequestMapping(value = "/logout")
			  public String logout(HttpServletRequest request) {
			    HttpSession session = request.getSession(false);
			    if (session != null) {
			      session.invalidate();
			    }
			    return "redirect:/";

			  }
			    
@RequestMapping(value = "/datafromUserPostService")	
public String datafromUserPostService(Model model, HttpServletRequest request, @RequestParam String email, @RequestParam String userName) {
    HttpSession session = request.getSession();



    session.setAttribute("email", email);
    session.setAttribute("userName", userName);
    session.setAttribute("loggedIn", true);

    return showAllPost(request, model);



  }
			  
			
			
}
