package com.dss.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dss.spring.model.Product;
import com.dss.spring.service.BagService;
import com.dss.spring.service.BillService;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@RestController
public class BillController {

	@Autowired
    private BagService bagService;

    @Autowired
    private BillService billService;

    @GetMapping("/bag/buy")
    public ResponseEntity<InputStreamResource> downloadInvoice(RedirectAttributes redirectAttributes) {

        Map<Product, Integer> products = bagService.getProductsInBag();
        double totalAmount = products.entrySet().stream()
            .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
            .sum();

        ByteArrayInputStream bis = billService.generateBillPdf(products, totalAmount);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=eFashionBill.pdf");
        
        try {
        	bagService.emptyBag();
            redirectAttributes.addFlashAttribute("message", "Products bought successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error buying products: " + e.getMessage());
        }
        
        return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
    }
}
