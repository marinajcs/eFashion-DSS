package com.dss.spring.controller;

import java.util.List;
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
    private UserService userService;

    @Autowired
    public BagController(BagService bagService, UserService userService) {
        this.bagService = bagService;
        this.userService = userService;
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
        User user = userService.getAuthenticatedUser();
        Bag userBag = bagService.getBagForUser(user);
        List<Product> productsInBag = userBag.getProducts();
        model.addAttribute("products", productsInBag);
        return "bag";
    }
}

