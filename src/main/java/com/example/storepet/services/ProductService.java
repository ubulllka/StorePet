package com.example.storepet.services;

import com.example.storepet.models.Animal;
import com.example.storepet.models.TypeProduct;
import com.example.storepet.repositories.OrderRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.storepet.models.Product;
import com.example.storepet.models.Person;
import com.example.storepet.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }
    public Optional<Product> getById(int id) {
        return productRepository.findById(id);
    }
    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }
    @Transactional
    public void delete(int id) {
        Product product = productRepository.findById(id).get();
        List<Person> peopleLiked = product.getLikedPerson();
        for (Person person : peopleLiked)
            person.removeLikedProduct(product);
        List<Person> peopleOrder = product.getOrderedPerson();
        for (Person person : peopleOrder)
            person.removeOrderProduct(product);
        productRepository.deleteById(id);
    }
    public Page<Product> getPage(String name, int page, int productsPerPage) {
        return productRepository.findByNameContainingIgnoreCase(name,
                PageRequest.of(page, productsPerPage, Sort.by("id").descending()));
    }
    public List<Product> getTopProducts() {
        return productRepository.findAll(PageRequest.of(0, 5,
                Sort.by("id").descending())).getContent();
    }
    @Transactional
    public void addLikedPerson(int id, Person person) {
        Product product = productRepository.findById(id).get();
        Hibernate.initialize(product.getLikedPerson());
        product.addLikedPerson(person);
    }
    public Person isLiked(int person_id, int product_id) {
        Product product = productRepository.findById(product_id).get();
        return product.getLikedPerson().stream()
                .filter(person -> person.getId() == person_id).findAny().orElse(null);
    }
    @Transactional
    public void deleteLikedPerson(int id, Person person) {
        Product product = productRepository.findById(id).get();
        product.getLikedPerson().remove(person);
    }
    private List<Product> generate100Products() {
        List<Product> products = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            String name = "Товар " + (i + 1);
            Product product = new Product();
            product.setName(name);
            product.setPrice((i%8 + 1)*600);
            product.setCount(i%10 + 1);
            product.setDescription("Lorem ipsum dolor sit amet, " +
                    "consectetuer adipiscing elit. Aenean commodo " +
                    "ligula eget dolor. Aenean massa. Cum sociis natoque " +
                    "penatibus et magnis dis parturient montes, nascetur " +
                    "ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu.");
            product.setAnimal(Animal.values()[i%5]);
            product.setTypeProduct(TypeProduct.values()[(i/5)%5]);
            products.add(product);
        }
        System.out.println(products);
        return products;
    }
    @Transactional
    public void batchUpd() {
        productRepository.saveAll(generate100Products());
    }
}
