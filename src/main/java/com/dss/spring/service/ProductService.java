package com.dss.spring.service;

import com.dss.spring.model.Bag;
import com.dss.spring.model.Product;
import com.dss.spring.repo.BagRepo;
import com.dss.spring.repo.ProductRepo;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private BagRepo bagRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product addProduct(Product product) {
        return productRepo.save(product);
    }
    
    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }
    
    public Product getProductById(Long id) {
		return productRepo.findById(id).orElse(null);
	}
    
    public void saveProduct(Product product) {
		productRepo.save(product);
	}

    @Transactional
    public void deleteProduct(Long productId) {
        List<Bag> bags = bagRepo.findAll();
        for (Bag bag : bags) {
            bag.getProducts().keySet().removeIf(product -> product.getId().equals(productId));
        }
        productRepo.deleteById(productId);
    }
	
}
