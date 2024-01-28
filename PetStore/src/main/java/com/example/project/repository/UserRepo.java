package com.example.project.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.model.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Long>{
	Optional<UserModel> findByEmail(String email);
	UserModel findByEmailAndPassword(String email,String password);
	
	
}
