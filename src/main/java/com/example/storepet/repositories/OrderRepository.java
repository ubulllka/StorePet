package com.example.storepet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.storepet.models.Order;
import com.example.storepet.models.OrderStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByProductIdAndPersonId(int productId, int personId);
    List<Order> findOrderByPersonIdAndOrderStatus(int personId, OrderStatus orderStatus);
    List<Order> findByProductIdAndOrderStatus(int productId, OrderStatus orderStatus);
    Optional<Order> findByProductIdAndPersonIdAndOrderStatus(int productId, int personId, OrderStatus orderStatus);
}




