package com.damian3111.demo.service;

import com.damian3111.demo.ProductDTO;
import com.damian3111.demo.entity.Product;
import com.damian3111.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Captor
    private ArgumentCaptor<Product> argumentCaptor;
    private ProductService underTest;

    @BeforeEach
    void beforeEach (){
        underTest = new ProductService(productRepository);
    }

    @Test
    void itShouldSaveProduct() {
        //given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(34.52f);
        productDTO.setName("book1");

        Product product = new Product();
        product.setId(1L);
        product.setPrice(34.52f);
        product.setName("book1");

        given(productRepository.save(any())).willReturn(product);
        //when
        Product savedProduct = underTest.saveProduct(productDTO);

        //then
        assertThat(savedProduct).isEqualTo(product);
        
        product.setId(null);
        then(productRepository).should().save(argumentCaptor.capture());
        Product argumentCaptorValue = argumentCaptor.getValue();
        assertThat(argumentCaptorValue).isEqualTo(product);
    }

    @Test
    void itShouldReturnAllProductsOrderedByPrice() {
        //given
        Product product1 = new Product();
        product1.setPrice(34.52f);
        product1.setName("book1");

        Product product2 = new Product();
        product2.setPrice(44.99f);
        product2.setName("book2");

        Product product3 = new Product();
        product3.setPrice(24.95f);
        product3.setName("book3");

        given(productRepository.findAllByPrice()).willReturn(Arrays.asList(product3, product1, product2));
        //when
        List<Product> orderedProducts = underTest.getOrderedProducts();

        //then
        assertThat(orderedProducts.get(0)).isEqualTo(product3);
        assertThat(orderedProducts.get(1)).isEqualTo(product1);
        assertThat(orderedProducts.get(2)).isEqualTo(product2);

        then(productRepository).should().findAllByPrice();
    }

}