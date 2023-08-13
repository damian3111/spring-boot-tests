package com.damian3111.demo.IntegrationTests;

import com.damian3111.demo.BaseIT;
import com.damian3111.demo.ProductDTO;
import com.damian3111.demo.entity.Product;
import com.damian3111.demo.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mapping.MappingException;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class IntegrationProductTest extends BaseIT {

    @Autowired
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void isShouldSaveProduct() throws Exception {
        //given
        Product product = new Product();
        product.setId(1L);
        product.setPrice(42.42f);
        product.setName("book");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(42.42f);
        productDTO.setName("book");

        //when
        ResultActions saveResult = mockMvc.perform(post("/saveProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(productDTO)));

        ResultActions getResult = mockMvc.perform(get("/getProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .param("productID", "1"));
        //then

        saveResult.andExpect(status().isOk());
        getResult.andExpect(status().isOk());

        String content = getResult.andReturn().getResponse().getContentAsString();
        assertThat(mapToObject(content)).isEqualTo(product);

    }




    public String mapToJson(ProductDTO product){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            throw new MappingException("Cannot map object to JSON");
        }

    }

    public Product mapToObject(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, Product.class);
        } catch (JsonProcessingException e) {
            throw new MappingException("Cannot map JSON to object");
        }
    }


}