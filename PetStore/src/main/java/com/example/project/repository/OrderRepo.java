package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.model.Order;
import com.example.project.model.UserModel;

@Repository
public interface OrderRepo  extends JpaRepository<Order, Long> {
    List<Order> findAllByUserOrderByCreatedDateDesc(UserModel user);

}