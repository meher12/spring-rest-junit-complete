package com.mdev.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdev.springboot.exception.ProductAlreadyExistsException;
import com.mdev.springboot.model.Product;
import com.mdev.springboot.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository){

        this.productRepository =productRepository;
    }

    @Override
    public Product addProduct(Product product) throws ProductAlreadyExistsException {
        if(productRepository.existsById(product.getId())){
            throw new ProductAlreadyExistsException();
        }
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {

        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product getProductByid(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product deleteProductById(int id) {
        Product product = null;
        Optional optional = null;
        //Optional optional = productRepository.findById(id);
        optional.ofNullable(productRepository.findById(id)).orElseThrow(RuntimeException::new);
        if (optional.isPresent()) {
            product = productRepository.findById(id).get();
            productRepository.deleteById(id);
        }
        return product;
    }

}