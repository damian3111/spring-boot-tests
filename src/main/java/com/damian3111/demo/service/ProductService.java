package com.damian3111.demo.service;

import com.damian3111.demo.ProductDTO;
import com.damian3111.demo.entity.Product;
import com.damian3111.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    public Product saveProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());

        return productRepository.save(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long productID) {
        return productRepository.findById(productID).orElseThrow();
    }

    public List<Product> getOrderedProducts() {
        return productRepository.findAllByPrice();
    }
}
