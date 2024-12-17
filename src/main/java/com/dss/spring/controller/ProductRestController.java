package com.dss.spring.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dss.spring.model.Product;
import com.dss.spring.service.BillService;
import com.dss.spring.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BillService databaseService;

	@ResponseBody
	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@ResponseBody
	@PostMapping("/add")
	public void addProduct(@RequestParam String name, @RequestParam double price) {
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		productService.saveProduct(product);
	}

	@ResponseBody
	@PostMapping("/edit")
	public void editProduct(@RequestParam Long productId, @RequestParam String name, @RequestParam double price) {
		Product product = productService.getProductById(productId);
		if (product != null) {
			product.setName(name);
			product.setPrice(price);
			productService.saveProduct(product);
		}
	}

	@ResponseBody
	@PostMapping("/delete")
	public void deleteProduct(@RequestParam Long productId) {
		productService.deleteProduct(productId);
	}

	@ResponseBody
	@GetMapping("/databaseExport")
	public void exportDatabase() throws SQLException, IOException {
        String path = databaseService.exportDatabase();
        File file = new File(path);
        FileSystemResource resource = new FileSystemResource(file);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.sql");
    }
}
