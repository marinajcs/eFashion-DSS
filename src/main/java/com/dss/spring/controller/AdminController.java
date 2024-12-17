package com.dss.spring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dss.spring.model.Product;
import com.dss.spring.service.ProductService;

@RequestMapping("/admin")
@Controller
public class AdminController {

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public String showAdmin(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "admin";
    }
	
	@GetMapping("/products")
	public String showProductsAdmin(Model model) {
		return this.showAdmin(model);
    }
	
	@PostMapping("/products/add")
    public String addProduct(@RequestParam("name") String name, 
                             @RequestParam("price") double price, 
                             RedirectAttributes redirectAttributes) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        productService.addProduct(product);

        redirectAttributes.addFlashAttribute("message", "Product added successfully!");
        return "redirect:/admin/products";
    }
	
	@PostMapping("/products/delete")
	public String deleteProduct(@RequestParam Long id, RedirectAttributes redirectAttributes) {
	    productService.deleteProduct(id);
	    redirectAttributes.addFlashAttribute("message", "Product removed successfully!");
	    return "redirect:/admin/products";
	}
	
	@PostMapping("/products/update")
	public String updateProduct(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("price") double price, RedirectAttributes redirectAttributes) {
	    Optional<Product> existingProduct = productService.findById(id);
	    
	    if (existingProduct.isPresent()) {
	        Product product = existingProduct.get();
	        product.setName(name);
	        product.setPrice(price);
	        productService.addProduct(product);
	        redirectAttributes.addFlashAttribute("message", "Product updated successfully!");
	    } else {
	    	redirectAttributes.addFlashAttribute("message", "Product updated successfully!");
	    }
	    return "redirect:/admin/products";
	}
	
	@GetMapping("/exportDB")
    public ResponseEntity<InputStreamResource> exportDatabase() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:file:./data/testdb", "sa", "password");

            String outputFile = "exportedDB.sql";
            Statement stmt = conn.createStatement();
            stmt.execute("SCRIPT TO 'exportedDB.sql'");

            stmt.close();
            conn.close();
            
            File file = new File(outputFile);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);

        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
 
}
