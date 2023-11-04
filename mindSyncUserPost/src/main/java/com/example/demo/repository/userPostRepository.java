package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.userPost;

@Repository
public interface userPostRepository extends JpaRepository<userPost, Integer>{

	Optional<userPost> findById(Integer postId);

	ArrayList<userPost> findByEmail(String email);

}
