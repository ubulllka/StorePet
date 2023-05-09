package com.example.storepet.controllers;

import com.example.storepet.models.Product;
import com.example.storepet.services.AuthService;
import com.example.storepet.services.OrderService;
import com.example.storepet.services.PersonService;
import com.example.storepet.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private PersonService peopleService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private AuthService authService;
    @BeforeEach
    public void setUp() {
       mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService,peopleService,orderService,authService)).build();
    }
    @Test
    void product() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setCount(5);
        when(productService.getById(1)).thenReturn(Optional.of(product));
        mockMvc.perform(get("/products/{id}", 1)).andExpect(status().isOk())
                .andExpect(view().name("product/productShow"));
    }
}