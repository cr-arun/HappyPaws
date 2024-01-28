package com.example.project.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.model.UserModel;
import com.example.project.model.WishList;

@Repository
public interface WishListRepo extends JpaRepository<WishList, Long> {
  public Optional<WishList> findByUser(UserModel user);
    
    

}
