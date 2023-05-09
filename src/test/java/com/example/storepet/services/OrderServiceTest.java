package com.example.storepet.services;


import com.example.storepet.models.Order;
import com.example.storepet.models.OrderStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceTest {
    @Mock
    private OrderService orderService;
    @Test
    void takeProduct() {
        Order order = new Order();
        order.setProductId(1);
        order.setPersonId(1);
        order.setOrderStatus(OrderStatus.ORDERED);
        order.setCount(5);
        orderService.save(order);

        orderService.takeProduct(order);
        verify(orderService, times(1)).takeProduct(order);
    }
    @Test
    void updateCount() {
        Order order = new Order();
        order.setId(1);
        order.setProductId(1);
        order.setPersonId(1);
        order.setOrderStatus(OrderStatus.ORDERED);
        order.setCount(5);
        orderService.save(order);

        orderService.updateCount(orderService.getById(1));
        verify(orderService, times(1)).updateCount(orderService.getById(1));
    }
}

