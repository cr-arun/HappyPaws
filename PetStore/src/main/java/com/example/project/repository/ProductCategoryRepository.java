package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.model.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {

}
