package com.example.storepet.controllers;

import com.example.storepet.models.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.storepet.models.*;
import com.example.storepet.services.*;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final PersonService peopleService;
    private final OrderService orderService;
    private final AuthService authService;

    @Autowired
    public ProductController(ProductService productService, PersonService peopleService,
                             OrderService orderService, AuthService authService) {
        this.productService = productService;
        this.peopleService = peopleService;
        this.orderService = orderService;
        this.authService = authService;
    }

    @GetMapping
    private String productsPage(@RequestParam(value = "search", defaultValue = "") String name,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "products_per_page", defaultValue = "15")int productsPerPage, Model model) {
        Optional<Person> optionalPerson = authService.getPerson();model.addAttribute("isAuthorised", false);
        if(optionalPerson.isPresent()) model.addAttribute("isAuthorised", true);
        model.addAttribute("products", productService.getPage(name, page, productsPerPage).get());
        model.addAttribute("productsPerPage", productsPerPage);
        model.addAttribute("search", name);
        model.addAttribute("pageCount", productService.getPage(name, page, productsPerPage).getTotalPages());
        return "product/products";
    }
    @GetMapping("/{id}")
    public String product(@PathVariable("id")int id, Model model) {
        Product product = productService.getById(id).get();
        model.addAttribute("product", product);
        if(product.getCount() == 0) {model.addAttribute("isFree", false);}
        model.addAttribute("isAuthorised", false);
        model.addAttribute("isLiked", null);model.addAttribute("isFree", true);
        Optional<Person> optionalPerson = authService.getPerson();
        if(optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            model.addAttribute("isAuthorised", true);
            model.addAttribute("isLiked", productService.isLiked(person.getId(), id));
            int count = 0;
            if (orderService.findOrderStatus(id, person.getId(), OrderStatus.ORDERED).isPresent()){
                count = orderService.findOrderStatus(id, person.getId(), OrderStatus.ORDERED).get().getCount();
            }
            model.addAttribute("count", count);
        }
        return "product/productShow";
    }

    @PostMapping("/{id}")
    public String likeProduct(@PathVariable("id")int id) {
        Person person = authService.getPerson().get();
        productService.addLikedPerson(id, peopleService.getPerson(id));
        peopleService.addLikedProduct(person.getId(), productService.getById(id).get());
        return "redirect:/products/"+id;
    }

    @DeleteMapping("/{id}")
    public String deleteLikeProduct(@PathVariable("id")int id) {
        Person person = authService.getPerson().get();
        productService.deleteLikedPerson(id, peopleService.getPerson(id));
        peopleService.deleteLikedProduct(person.getId(), productService.getById(id).get());
        return "redirect:/products/"+id;
    }
}
