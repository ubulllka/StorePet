package com.example.storepet.controllers;

import com.example.storepet.models.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.storepet.dto.OrderDTO;
import com.example.storepet.services.ProductService;
import com.example.storepet.services.OrderService;
import com.example.storepet.services.PersonService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PersonService peopleService;
    private final ProductService productService;
    private final OrderService orderService;

    @Autowired
    public AdminController(PersonService peopleService, ProductService productService, OrderService orderService) {
        this.peopleService = peopleService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/products")
    public String productsPage(@RequestParam(value = "search", defaultValue = "", required = false) String name,
                            @RequestParam(value = "page", defaultValue = "0")int page,
                            Model model) {
        Page page1 =  productService.getPage(name, page, 40);
        model.addAttribute("products", page1);
        model.addAttribute("search", name);
        model.addAttribute("pageCount", page1.getTotalPages());
        return "admin/products";
    }

    @GetMapping("/add")
    public String addProductPage(@ModelAttribute("product") Product product, Model model) {
        model.addAttribute("animals", Animal.values());
        model.addAttribute("types", TypeProduct.values());
        return "admin/productAdd";
    }

    @PostMapping("/add")
    public String saveProduct(@ModelAttribute("product") Product product,ModelMap model){
        productService.save(product);
        model.addAttribute("id", product.getId());
        return "redirect:/admin/products";
    }

    @GetMapping("/products/{id}")
    public String productPage(@PathVariable("id") int id,
                           Model model) {
        model.addAttribute("product", productService.getById(id).get());
        List<Order> ordered = orderService.findByBookIdAnsStatus(id, OrderStatus.ORDERED);
        List<Order> took = orderService.findByBookIdAnsStatus(id, OrderStatus.TOOK);
        model.addAttribute("ordered", ordered.stream().map(this::ConvertToOrderDTO).collect(Collectors.toList()));
        model.addAttribute("took", took.stream().map(this::ConvertToOrderDTO).collect(Collectors.toList()));
        return "admin/productShow";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.delete(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/people")
    public String peoplePage(@RequestParam(value = "search", defaultValue = "", required = false)String name, Model model) {
        model.addAttribute("people", peopleService.findByName(name));
        return "admin/people";
    }

    @GetMapping("/people/{id}")
    public String personPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.getPerson(id));
        List<Order> orderedProducts = orderService.findOrderByPersonIdAndOrderStatus(id, OrderStatus.ORDERED);
        List<Order> tooProducts = orderService.findOrderByPersonIdAndOrderStatus(id, OrderStatus.TOOK);
        model.addAttribute("orderedProducts", orderedProducts.stream().map(this::ConvertToOrderDTO).collect(Collectors.toList()));
        model.addAttribute("tookProducts", tooProducts.stream().map(this::ConvertToOrderDTO).collect(Collectors.toList()));
        return "admin/personShow";
    }

    @PostMapping("/order")
    public String takeProduct(@RequestParam("order")int id) {
        Order order = orderService.getById(id);
        orderService.takeProduct(order);
        return "redirect:/admin/people/" + order.getPersonId();
    }


    @DeleteMapping("/order")
    public String deleteProductfromOrder(@RequestParam("order")int id) {
        Order order = orderService.getById(id);
        orderService.delete(order);
        return "redirect:/admin/people/" + order.getPersonId();
    }

    @PostMapping("/products/order")
    public String take(@RequestParam("order")int id) {
        Order order = orderService.getById(id);
        orderService.takeProduct(order);
        return "redirect:/admin/products/" + order.getProductId();
    }

    @DeleteMapping("/products/order")
    public String delete(@RequestParam("order")int id) {
        Order order = orderService.getById(id);
        orderService.delete(order);
        return "redirect:/admin/products/" + order.getProductId();
    }


    @GetMapping("/batch")
    public String batchUpd() {
        productService.batchUpd();
        return "redirect:/admin/products";
    }

    private OrderDTO ConvertToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setPerson(peopleService.getPerson(order.getPersonId()));
        orderDTO.setProduct(productService.getById(order.getProductId()).get());
        orderDTO.setCount(order.getCount());
        return orderDTO;
    }
}
