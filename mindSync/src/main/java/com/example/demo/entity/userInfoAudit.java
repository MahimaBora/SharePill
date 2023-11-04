package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="userInfo")
public class userInfoAudit {
	
	@Id
	private String id;
    @Indexed(unique = true)
	private String email;
	private String userName;
	private String password;
	private LocalDateTime timeStamp = LocalDateTime.now();
	

}
