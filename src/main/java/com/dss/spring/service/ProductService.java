package com.dss.spring.service;

import com.dss.spring.model.Product;
import com.dss.spring.repo.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
	private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepository) {
        this.productRepo = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
	
}
