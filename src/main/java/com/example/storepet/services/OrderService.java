package com.example.storepet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.storepet.models.Order;
import com.example.storepet.models.OrderStatus;
import com.example.storepet.repositories.OrderRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public Order getById(int id) {
        return orderRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(Order order) {
        orderRepository.save(order);
    }
    @Transactional
    public void delete(Order order) {
        orderRepository.delete(order);
    }
    public Optional<Order> findOrder(int productId, int personId) {
        return orderRepository.findByProductIdAndPersonId(productId, personId);
    }
    public Optional<Order> findOrderStatus(int productId, int personId, OrderStatus orderStatus) {
        return orderRepository.findByProductIdAndPersonIdAndOrderStatus(productId, personId,orderStatus);
    }
    public List<Order> findByBookIdAnsStatus(int productId, OrderStatus orderStatus) {
        return orderRepository.findByProductIdAndOrderStatus(productId, orderStatus);
    }
    public List<Order> findOrderByPersonIdAndOrderStatus(int personId, OrderStatus orderStatus) {
        return orderRepository.findOrderByPersonIdAndOrderStatus(personId, orderStatus);
    }
    @Transactional
    public void takeProduct(Order order) {
        if (orderRepository.findByProductIdAndPersonIdAndOrderStatus
                (order.getProductId(), order.getPersonId(), OrderStatus.TOOK).isEmpty()){
            order.setOrderStatus(OrderStatus.TOOK);
            orderRepository.save(order);
        }
        else {
            Order oldOrder = orderRepository.findByProductIdAndPersonIdAndOrderStatus
                    (order.getProductId(), order.getPersonId(), OrderStatus.TOOK).get();
            int count = oldOrder.getCount();
            oldOrder.setCount(count + order.getCount());
            orderRepository.delete(order);
            orderRepository.save(oldOrder);
        }
    }
    public void updateCount(Order order){
        int count = order.getCount();
        order.setCount(count + 1);
        orderRepository.save(order);
    }
}
