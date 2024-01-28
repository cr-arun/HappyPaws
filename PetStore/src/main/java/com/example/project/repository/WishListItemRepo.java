package com.example.project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.model.WishListItem;

@Repository
public interface WishListItemRepo extends JpaRepository<WishListItem, Long>{

}
