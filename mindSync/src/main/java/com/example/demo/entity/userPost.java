package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class userPost {
	
	private Integer id;
	public String email;		
	public String userName;
	public String post;
	public Integer likeCount;
	private LocalDateTime timeStamp;


}
