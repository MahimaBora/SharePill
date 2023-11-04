package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.userInfo;
import com.example.demo.entity.userPost;
import com.example.demo.interfaces.userPostInterface;
import com.example.demo.repository.userPostRepository;

@Service
public class userPostService implements userPostInterface {
	
	@Autowired
	userPostRepository userPostRepo;
	
	@Autowired
	userPost userPost;
	
	@Autowired
	RestTemplate restTemplate;
	

	@Autowired
	userInfo userInfo;

	@Override
	public String createPost(String email, String userName, String userPost) {
		// TODO Auto-generated method stub
	
	   userPost post = new userPost();
		
	   post.setEmail(email);
	   post.setUserName(userName);
	   post.setPost(userPost);
	   userPostRepo.save(post);		

		
		return "postCreated";
	}

	@Override
	public ArrayList<userPost> showPost() {
		// TODO Auto-generated method stub
//		ArrayList <userPost> list = (ArrayList<userPost>) userPostRepo.findAll();
		return (ArrayList<userPost>) userPostRepo.findAll();
	}
	
	@Override
	public void saveRating(Integer postId) {
	    // Find the userPost entities with the given postId
	    Optional<userPost> list = userPostRepo.findById(postId);
	    
	    if (list.isPresent()) {
	        userPost userPostEntity = list.get();
	        userPostEntity.setLikeCount(userPostEntity.getLikeCount() + 1);
	        userPostRepo.save(userPostEntity);
	    } 
	    
//	    for (userPost i : list) {
//	        i.setLikeCount(i.getLikeCount() + 1);
//	        userPostRepo.save(i); // Save the individual userPost entity
//	    }
	}

	@Override
	public ArrayList<userPost> showOnePost(String email) {
		// TODO Auto-generated method stub
		return (ArrayList<userPost>) userPostRepo.findByEmail(email);

	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		userPostRepo.deleteById(postId);
		
	}



}
