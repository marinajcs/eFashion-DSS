package com.dss.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dss.spring.model.Product;
import com.dss.spring.service.BagService;
import com.dss.spring.service.ProductService;

@RequestMapping("/catalog")
@Controller
public class CatalogController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BagService bagService;

	@GetMapping
	public String showCatalog(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "catalog";
    }
	
	@PostMapping("/add")
    public String addProductToBag(@RequestParam("productId") Long productId, RedirectAttributes redirectAttributes) {
        try {
            bagService.addProductToBag(productId);
            redirectAttributes.addFlashAttribute("message", "Product added to your bag successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding product to bag: " + e.getMessage());
        }
        return "redirect:/bag";
    }

}
