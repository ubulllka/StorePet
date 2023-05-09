package com.example.storepet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.storepet.dto.OrderDTO;
import com.example.storepet.models.*;
import com.example.storepet.services.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class PersonController {
    private final PersonService peopleService;
    private final OrderService orderService;
    private final ProductService booksService;
    private final AuthService authService;

    @Autowired
    public PersonController(PersonService peopleService, OrderService orderService,
                            ProductService booksService, AuthService authService) {
        this.peopleService = peopleService;
        this.orderService = orderService;
        this.booksService = booksService;
        this.authService = authService;
    }

    @GetMapping("/liked_products")
    public String likedProductPage(Model model) {
        Person person = authService.getPerson().get();
        model.addAttribute("products", peopleService.getLikedProducts(person.getId()));
        return "person/likedProducts";
    }

    @GetMapping
    public String profilePage(Model model) {
        Person person = authService.getPerson().get();
        model.addAttribute("person", person);
        List<Order> orderedProducts = orderService.findOrderByPersonIdAndOrderStatus(person.getId(), OrderStatus.ORDERED);
        List<Order> tookProducts = orderService.findOrderByPersonIdAndOrderStatus(person.getId(), OrderStatus.TOOK);
        model.addAttribute("orderedProducts", orderedProducts.stream()
                .map(this::ConvertToOrderDTO).collect(Collectors.toList()));
        model.addAttribute("tookProducts", tookProducts.stream()
                .map(this::ConvertToOrderDTO).collect(Collectors.toList()));
        return "person/profile";
    }

    private OrderDTO ConvertToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setPerson(peopleService.getPerson(order.getPersonId()));
        orderDTO.setProduct(booksService.getById(order.getProductId()).get());
        orderDTO.setCount(order.getCount());
        return orderDTO;
    }

    @GetMapping("/edit")
    public String editPage(Model model) {
        Person person = authService.getPerson().get();
        model.addAttribute("person", person);
        return "person/edit";
    }
    @PatchMapping("/edit")
    public String edit(@ModelAttribute("person") Person person) {
        Person personAuth = authService.getPerson().get();
        peopleService.update(personAuth.getId(), person);
        authService.getPersonDetails().update(person);
        return "redirect:/profile";
    }
}
