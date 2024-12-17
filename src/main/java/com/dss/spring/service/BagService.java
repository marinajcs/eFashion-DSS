package com.dss.spring.service;

import com.dss.spring.model.Bag;
import com.dss.spring.model.Product;
import com.dss.spring.model.User;
import com.dss.spring.repo.BagRepo;
import com.dss.spring.repo.ProductRepo;
import com.dss.spring.repo.UserRepo;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private UserRepo userRepo;

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
        bag.remove1Product(product);
        bagRepo.save(bag);
    }

    public Map<Product, Integer> getProductsInBag() {
        User user = userService.getAuthenticatedUser();
        Bag bag = getBagForUser(user);
        return bag.getProducts();
    }
    
    public Map<Product, Integer> getCart(String username){
		User user = userRepo.findByUsername(username);
		Bag cart = this.getBagForUser(user);
		return cart.getProducts();
	}
    
    public void emptyBag() throws RuntimeException {
    	User user = userService.getAuthenticatedUser();
        Bag bag = getBagForUser(user);
        bag.emptyBag();
        bagRepo.save(bag);
    }
    
    public double getTotal() {
    	User user = userService.getAuthenticatedUser();
        Bag bag = getBagForUser(user);
		return bag.getTotal();
	}
    
}


