package com.damian3111.demo.repository;

import com.damian3111.demo.BaseIT;
import com.damian3111.demo.entity.Product;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import jakarta.validation.ConstraintViolationException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest extends BaseIT {
    @Autowired
    private ProductRepository underTest;

    @Test
    void itShouldSaveProduct() {
        //given
        Product product = new Product();
        product.setPrice(44.99f);
        product.setName("book");

        //whem
        underTest.save(product);

        //then
        Optional<Product> savedProduct = underTest.findById(1L);
        assertThat(savedProduct).hasValue(product);
    }

    @RepeatedTest(5)
    void itShouldReturnProductsOrderedByPrice() {
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
        underTest.saveAll(Arrays.asList(product1, product2, product3));

        //when
        List<Product> allByPrice = underTest.findAllByPrice();
        //then
        assertThat(allByPrice.get(0)).isEqualTo(product3);
        assertThat(allByPrice.get(1)).isEqualTo(product1);
        assertThat(allByPrice.get(2)).isEqualTo(product2);

    }

    @ParameterizedTest
    @CsvSource({"34.23, bo", "0.5, book1"})
    void itShouldNotSaveProductWhenPropertiesAreWrong(float price, String name) {
        //given
        Product product = new Product();
        product.setPrice(price);
        product.setName(name);

        //when
        //then
        assertThatThrownBy(() -> underTest.save(product))
                .isInstanceOf(ConstraintViolationException.class);
    }



}