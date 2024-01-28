package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.model.OrderItem;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItem,Integer> {
	
}