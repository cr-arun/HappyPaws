package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.Product;
import com.example.project.model.Review;

public interface ReviewRepo extends JpaRepository<Review, Long>{
	List<Review> findAllByProduct(Product product);
}
