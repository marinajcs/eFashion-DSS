package com.dss.spring.service;

import com.dss.spring.model.Bag;
import com.dss.spring.model.Product;
import com.dss.spring.model.User;
import com.dss.spring.repo.BagRepo;
import com.dss.spring.repo.ProductRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BagService {

    @Autowired
    private BagRepo bagRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserService userService;

    public Bag getBagForUser(User user) {
        return bagRepo.findByUser(user).orElseGet(() -> {
            Bag bag = new Bag();
            bag.setUser(user);
            return bagRepo.save(bag);
        });
    }

    public void addProductToBag(Long productId) throws RuntimeException {
        User user = userService.getAuthenticatedUser();
        Bag bag = getBagForUser(user);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        bag.addProduct(product);
        bagRepo.save(bag);
    }

    public void removeProductFromBag(Long productId) throws RuntimeException {
        User user = userService.getAuthenticatedUser();
        Bag bag = getBagForUser(user);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        bag.removeProduct(product);
        bagRepo.save(bag);
    }

    public List<Product> getProductsInBag() {
        User user = userService.getAuthenticatedUser();
        Bag bag = getBagForUser(user);
        return bag.getProducts();
    }
}


