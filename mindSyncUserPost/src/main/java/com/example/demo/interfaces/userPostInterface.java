package com.example.demo.interfaces;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.example.demo.entity.userPost;

public interface userPostInterface {
	
	public String createPost(String email, String userName, String userPost);

	public ArrayList<userPost> showPost();

	public void saveRating(Integer postId);

	public ArrayList<userPost> showOnePost(String email);

	public void deletePost(Integer postId);
	

}
