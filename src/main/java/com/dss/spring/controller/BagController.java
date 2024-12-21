package com.dss.spring.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
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

import com.dss.spring.model.Bag;
import com.dss.spring.model.Product;
import com.dss.spring.model.User;
import com.dss.spring.service.BagService;
import com.dss.spring.service.BillService;
import com.dss.spring.service.UserService;

@RequestMapping("/bag")
@Controller
public class BagController {

	@Autowired
    private BagService bagService;
	
	@Autowired
    private BillService billService;
	
    @GetMapping
    public String showUserBag(Model model) {
    	Map<Product, Integer> productsInBag = bagService.getProductsInBag();
        double total = bagService.getTotal();
        model.addAttribute("products", productsInBag);
        model.addAttribute("total", total);
        return "bag";
    }
    
    @PostMapping("/add")
    public String add1MoreProduct(@RequestParam("productId") Long productId, RedirectAttributes redirectAttributes) {
        try {
            bagService.addProductToBag(productId);
            redirectAttributes.addFlashAttribute("message", "Product quantity increased successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error increasing product quantity: " + e.getMessage());
        }
        return "redirect:/bag";
    }

    @PostMapping("/remove")
    public String removeProductFromBag(@RequestParam("productId") Long productId, RedirectAttributes redirectAttributes) {
        try {
            bagService.removeProductFromBag(productId);
            redirectAttributes.addFlashAttribute("message", "Product removed from your bag successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error removing product from bag: " + e.getMessage());
        }
        return "redirect:/bag";
    }
    
    @GetMapping("/empty")
    public String emptyBag(RedirectAttributes redirectAttributes) {
        try {
            bagService.emptyBag();
            redirectAttributes.addFlashAttribute("message", "Products bought successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error emptying bag: " + e.getMessage());
        }
        return "redirect:/bag";
    }
    
    @GetMapping("/buy")
    public ResponseEntity<InputStreamResource> downloadInvoice(RedirectAttributes redirectAttributes) {

        Map<Product, Integer> products = bagService.getProductsInBag();
        double totalAmount = products.entrySet().stream()
            .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
            .sum();

        ByteArrayInputStream bis = billService.generateBillPdf(products, totalAmount);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=eFashionBill.pdf");
        
        return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
    }
    
}

