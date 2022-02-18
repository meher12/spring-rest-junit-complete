package com.mdev.springboot.service;

import java.util.List;

import com.mdev.springboot.exception.ProductAlreadyExistsException;
import com.mdev.springboot.model.Product;

public interface ProductService {

     Product addProduct(Product product) throws ProductAlreadyExistsException;
    List<Product> getAllProducts();
     Product getProductByid(int id);
    Product deleteProductById(int id);



}