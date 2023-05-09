package com.example.storepet.services;

import com.example.storepet.models.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {
    @Mock
    private ProductService productService;

    @Test
    void delete() {
        Product product = new Product();
        product.setId(1);
        product.setLikedPerson(new ArrayList<>());
        product.setOrderedPerson(new ArrayList<>());

        productService.delete(1);
        verify(productService, times(1)).delete(1);
    }
    @Test
    void batchUpd() {
        productService.batchUpd();
        verify(productService, times(1)).batchUpd();
    }
}