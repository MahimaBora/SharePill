package com.example.demo.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.userInfoAudit;


@Service
public interface mindSyncMongoInterface {
	

	
	//create User record
	public void saveUserInfo(String email,String userName,String password);
	
	//getOneUser
	public String getOneUser(String email, String userName, String password, String funName, String newPassword);

	 public String sendEmail(String mail) throws Exception;

	public List<userInfoAudit> getOneUser(String email);

	public String updateDetail(String email, String fullname, String password);
	 



}
