package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.userInfoAudit;

@Repository
public interface UserRepository extends MongoRepository<userInfoAudit,String>{

	@Query("{email:?0}")
	List<userInfoAudit> findByEmail(String email);
	
//	@Query(value="{}",fields="{'id':1,'email':1}")
//	List<userInfoAudit>findEmail();
	

	// @Query("{}") for all docs

//	userInfoAudit saveUserInfo(userInfoAudit userInfoAud);
	

}

