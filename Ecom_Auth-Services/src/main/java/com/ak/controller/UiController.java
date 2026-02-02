package com.ak.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class UiController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/products")
    public String productsPage(Model model, Principal principal) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Calls", "AUTH_SERVICE");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> rawResponse = restTemplate.exchange(
                "http://localhost:8086/products",
                HttpMethod.GET,
                entity,
                String.class
            );

            System.out.println("📦 Raw response: " + rawResponse.getBody());

            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> products = mapper.readValue(
                rawResponse.getBody(),
                new TypeReference<List<Map<String, Object>>>() {}
            );

            System.out.println("📦 Parsed size: " + products.size());
            model.addAttribute("products", products);  // ✅ lowercase p

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            model.addAttribute("products", new ArrayList<>());
        }

        model.addAttribute("username", principal.getName());  // ✅ comma not +
        return "products";
    }

    @GetMapping("/orders")
    public String ordersPage(Model model, Principal principal) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Calls", "AUTH_SERVICE");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List> response = restTemplate.exchange(
                "http://localhost:8086/orders",
                HttpMethod.GET,
                entity,
                List.class
            );
            model.addAttribute("orders", response.getBody());
        } catch (Exception e) {
            System.out.println("⚠️ Order service not available: " + e.getMessage());
            model.addAttribute("orders", new ArrayList<>());
        }

        model.addAttribute("username", principal.getName());
        return "orders";
    }
}