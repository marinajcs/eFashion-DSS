package com.dss.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dss.spring.model.Bag;
import com.dss.spring.model.Product;
import com.dss.spring.model.User;
import com.dss.spring.service.BagService;
import com.dss.spring.service.UserService;

@Controller
public class BagController {

    private BagService bagService;

    @Autowired
    public BagController(BagService bagService, UserService userService) {
        this.bagService = bagService;
    }

    @PostMapping("/catalog/add")
    public String addProductToBag(@RequestParam("productId") Long productId, RedirectAttributes redirectAttributes) {
        try {
            bagService.addProductToBag(productId);
            redirectAttributes.addFlashAttribute("message", "Product added to your bag successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding product to bag: " + e.getMessage());
        }
        return "redirect:/bag";
    }
    
    @PostMapping("/bag/add")
    public String add1MoreProduct(@RequestParam("productId") Long productId, RedirectAttributes redirectAttributes) {
        try {
            bagService.addProductToBag(productId);
            redirectAttributes.addFlashAttribute("message", "Product quantity increased successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error increasing product quantity: " + e.getMessage());
        }
        return "redirect:/bag";
    }

    @PostMapping("/bag/remove")
    public String removeProductFromBag(@RequestParam("productId") Long productId, RedirectAttributes redirectAttributes) {
        try {
            bagService.removeProductFromBag(productId);
            redirectAttributes.addFlashAttribute("message", "Product removed from your bag successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error removing product from bag: " + e.getMessage());
        }
        return "redirect:/bag";
    }

    @GetMapping("/bag")
    public String showUserBag(Model model) {
        Map<Product, Integer> productsInBag = bagService.getProductsInBag();
        model.addAttribute("products", productsInBag);
        return "bag";
    }
    
    @GetMapping("/bag/empty")
    public String emptyBag(RedirectAttributes redirectAttributes) {
        try {
            bagService.emptyBag();
            redirectAttributes.addFlashAttribute("message", "Products bought successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error emptying bag: " + e.getMessage());
        }
        return "redirect:/bag";
    }
    
}

