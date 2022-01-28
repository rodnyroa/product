package com.example.product.core.order.service.impl;

import com.example.product.core.exception.NotFoundException;
import com.example.product.core.order.domain.Order;
import com.example.product.core.order.repository.OrderRepository;
import com.example.product.core.order.service.OrderSeeker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class OrderSeekerImpl implements OrderSeeker {

    private OrderRepository repository;

    @Autowired
    public OrderSeekerImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {

        Page<String> page = this.repository.findGroupByNumber(pageable);
        List<String> orderNumbers = page.getContent();

        List<Order> orders = this.getOrdersByOrderNumber(orderNumbers);

        return new PageImpl<>(orders, pageable, page.getTotalElements());
    }

    @Override
    public Page<Order> findAllOrdersBetweenOrderDate(Pageable pageable, LocalDate orderStartDate, LocalDate orderEndDate) {
        Page<String> page = this.repository.findByOrderTimeBetweenAndGroupByNumber(pageable,orderStartDate,orderEndDate);
        List<String> orderNumbers = page.getContent();

        List<Order> orders = this.getOrdersByOrderNumber(orderNumbers);

        return new PageImpl<>(orders, pageable, page.getTotalElements());
    }

    @Override
    public List<Order> findById(String number) {
        String msg = String.format("Invalid id: %s", number);
        List<Order> orders = this.repository.findByNumber(number);
        if (orders.isEmpty()) {
            throw new NotFoundException(msg);
        }
        return orders;
    }

    private List<Order> getOrdersByOrderNumber(List<String> orderNumbers) {
        return this.repository.findByNumberIn(orderNumbers);
    }
}
