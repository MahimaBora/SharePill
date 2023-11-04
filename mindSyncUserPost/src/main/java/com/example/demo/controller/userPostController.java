package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.userPost;
import com.example.demo.interfaces.userPostInterface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
//@RequestMapping("/mindSync")
public class userPostController {
	
	public  String emailFixed;
	public  String userNameFixed;

	@Autowired 
	userPostInterface userPostInt;
	
	@Autowired
	RestTemplate restTemplate;
	
	userPostController(userPostInterface userPostInt){
		this.userPostInt = userPostInt;
	}
	
	@RequestMapping("/createPost")
	public String createPost(Model model, HttpServletRequest request,@RequestParam String email, @RequestParam String userName) {  
		
		HttpSession session= request.getSession();
		session.setAttribute("email", email);
		session.setAttribute("userName",userName);
		session.setAttribute("loggedIn", true); 

		this.emailFixed=email;
		this.userNameFixed=userName;
		
		
		model.addAttribute("email",email);
		model.addAttribute("userName", userName);

	    return "post.html";
	} 
	

	
	@RequestMapping("/savePost")
	public String generatePost(HttpServletRequest request,Model model,String userPost) {	  
		
		HttpSession session= request.getSession();
		String email= (String) session.getAttribute("email");
		String userName= (String)session.getAttribute("userName"); 

		model.addAttribute("email",email);
		model.addAttribute("userName", userName);

		String str=  userPostInt.createPost(this.emailFixed,this.userNameFixed,userPost);
		 if(str.equals("postCreated")) {
				model.addAttribute("result", "You've posted! Great job! Keep 'em coming!");

		 }
		 else {
				model.addAttribute("result", "Error");

		 }
		 

	    return "post";
	} 
	
	
	
	
	@ResponseBody
	@GetMapping("/showPost")
	public ArrayList <userPost> showPost() {	  
		ArrayList <userPost> showPostlist = userPostInt.showPost();
	    return showPostlist; 
	} 
	
	@ResponseBody
	@GetMapping("/showOnePost")
	public ArrayList <userPost> showOnePost(@RequestParam String email) {	  
		ArrayList <userPost> showOnePostlist = userPostInt.showOnePost(email);
	    return showOnePostlist; 
	} 
	
	@ResponseBody
	@RequestMapping("/rate")
	public void rating(@RequestParam Integer postId) {
		userPostInt.saveRating(postId);
	} 
	
	@ResponseBody
	@RequestMapping("/deleteUserPost")
	public void deleteUserPost(@RequestParam Integer postId) {
		userPostInt.deletePost(postId);
	} 
	
	
//	  @ResponseBody
//	  
//	  @RequestMapping(value = "/") 
//	  public String getpostPage(HttpServletRequest request) { HttpSession session = request.getSession(); String email =
//	  session.getAttribute("email").toString(); String userName =
//	  session.getAttribute("userName").toString();
//	  System.out.println("name is =====" + userName);
//	  
//	  return restTemplate.getForObject(
//	  "http://localhost:8081/mindSync/datafromUserPostService"+"?email="+email+"&userName="+userName,String.class);
//	  }
	
	 @RequestMapping(value = "/")
	    public String getpostPage(HttpServletRequest request) {
		 HttpSession session = request.getSession();
         String email = session.getAttribute("email").toString();
         String userName = session.getAttribute("userName").toString();
         String url = "https://Home-Service.azurewebsites.net/datafromUserPostService" + "?email=" + email + "&userName=" + userName;
	            return "redirect:" + url; // Redirect the user to the specified URL
	       
	    }	 

}
//ArrayList<Rating> ratingInfo=restTemplate.getForObject("http://RATINGSERVICE/rating/users/"+userId, ArrayList.class);
