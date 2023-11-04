package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="userPost")
public class userPost {
	

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	public String email;		
	public String userName ;
//	@Lob
	public String post;
	public Integer likeCount=0;
	private LocalDateTime timeStamp = LocalDateTime.now();



}
