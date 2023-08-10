package com.damian3111.demo.controller;

import com.damian3111.demo.ProductDTO;
import com.damian3111.demo.entity.Product;
import com.damian3111.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getProduct")
    public Product getProduct(@RequestParam("productID") Long productID){
        return productService.getProduct(productID);
    }

    @GetMapping("/getProducts")
    public List<Product> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/getProducts/ordered")
    public List<Product> getAllProductsOrdered() {
        return productService.getOrderedProducts();
    }

    @PostMapping("/saveProduct")
    public Product saveProduct(@RequestBody ProductDTO productDTO){
            return productService.saveProduct(productDTO);
    }
}
