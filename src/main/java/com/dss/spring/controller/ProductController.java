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
public class ProductController {

	private ProductService productService;
	
	@Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
	
	@PostMapping("/admin/products/add")
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
	
	@PostMapping("/admin/products/delete")
	public String deleteProduct(@RequestParam Long id, RedirectAttributes redirectAttributes) {
	    productService.deleteProduct(id);
	    redirectAttributes.addFlashAttribute("message", "Product removed successfully!");
	    return "redirect:/admin/products";
	}

	@GetMapping("/admin/products")
	public String showCatalogAdmin(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "admin";
   }
	
	@GetMapping("/admin")
	public String showAdmin(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "admin";
   }
 
	@GetMapping("/catalog")
	public String showCatalog(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "catalog";
   }

}
