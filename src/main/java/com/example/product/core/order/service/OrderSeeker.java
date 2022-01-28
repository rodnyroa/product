package com.example.product.core.order.service;

import com.example.product.core.order.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderSeeker {

    Page<Order> findAll(Pageable pageable);

    Page<Order> findAllOrdersBetweenOrderDate(Pageable pageable, LocalDate orderStartDate, LocalDate orderEndDate);

    List<Order> findById(String number);
}
