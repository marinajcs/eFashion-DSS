package com.dss.spring.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dss.spring.model.Product;
import com.dss.spring.service.BagService;
import com.dss.spring.service.BillService;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {
	
	@Autowired
	private BagService cartService;
	
	@Autowired
	private BillService billService;
	
	@ResponseBody
	@GetMapping
	public Map<Product, Integer> getCart() {
        return cartService.getProductsInBag();
	}
	
	@ResponseBody
	@GetMapping("/total")
	public double getTotalPrice() {
        return cartService.getTotal();
	}
	
	@ResponseBody
	@PostMapping("/add")
    public void saveProduct(@RequestParam Long productId) {
        cartService.addProductToBag(productId);
    }
	
	@ResponseBody
	@PostMapping("/delete")
    public void deleteProduct(@RequestParam Long productId) {
		cartService.removeProductFromBag(productId);
    }
	
	@ResponseBody
	@GetMapping("/bill")
    public ResponseEntity<InputStreamResource> downloadInvoice(RedirectAttributes redirectAttributes) {

        Map<Product, Integer> products = cartService.getProductsInBag();
        double totalAmount = cartService.getTotal();

        ByteArrayInputStream bis = billService.generateBillPdf(products, totalAmount);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=bill.pdf");
        
        cartService.emptyBag();
        
        return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
    }
}
