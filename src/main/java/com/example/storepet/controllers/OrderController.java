package com.example.storepet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.storepet.models.*;
import com.example.storepet.services.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final ProductService productService;
    private final AuthService authService;
    private final OrderService orderService;

    @Autowired
    public OrderController(ProductService productService,
                           AuthService authService, OrderService orderService) {
        this.productService = productService;
        this.authService = authService;
        this.orderService = orderService;
    }

    @PostMapping("/add/{id}")
    public String addProduct(@PathVariable("id")int id) {
        Person person = authService.getPerson().get();
        Product product = productService.getById(id).get();
        int count = product.getCount() - 1;
        product.setCount(count);
        Order order;
        if (orderService.findOrderStatus(product.getId(), person.getId(), OrderStatus.ORDERED).isPresent()){
            order = orderService.findOrderStatus(product.getId(), person.getId(), OrderStatus.ORDERED).get();
            orderService.updateCount(order);
        }
        else {
            order = new Order();
            order.setPersonId(person.getId());
            order.setProductId(product.getId());
            order.setCount(1);
            order.setOrderStatus(OrderStatus.ORDERED);
        }
        orderService.save(order);

        return "redirect:/products/" + id;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        Person person = authService.getPerson().get();
        Order order = orderService.findOrder(id, person.getId()).get();
        orderService.delete(order);
        return "redirect:/products/" + id;
    }
}
