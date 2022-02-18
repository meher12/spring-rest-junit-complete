package com.mdev.springboot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdev.springboot.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}