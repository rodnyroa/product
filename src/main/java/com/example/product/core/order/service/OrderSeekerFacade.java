package com.example.product.core.order.service;

import com.example.product.core.order.domain.Order;
import com.example.product.core.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderSeekerFacade {
    Page<Order> findAllOrders(Pageable pageable);

    Page<Order> findAllOrdersBetweenOrderDate(Pageable pageable, LocalDate orderStartDate, LocalDate orderEndDate);

    List<Order> findOrderById(String number);

    Product findProductById(Integer productId);
}
