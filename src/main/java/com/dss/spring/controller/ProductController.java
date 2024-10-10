package com.dss.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dss.spring.model.Product;
import com.dss.spring.service.ProductService;

@Controller
//@RequestMapping("/admin")
public class ProductController {

	private ProductService productService;
	
	@Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
	
	@PostMapping("/admin/addProduct")
    public String addProduct(@RequestParam("name") String name, 
                             @RequestParam("price") double price, 
                             RedirectAttributes redirectAttributes) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        productService.addProduct(product);

        redirectAttributes.addFlashAttribute("message", "Product added successfully!");
        return "redirect:/admin";
    }
	/*
    @GetMapping("/admin")
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin";
    }
    */

}
