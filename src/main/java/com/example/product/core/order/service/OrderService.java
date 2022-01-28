package com.example.product.core.order.service;

import com.example.product.core.order.dto.OrderRequestDto;
import com.example.product.core.order.dto.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface OrderService {

    Page<OrderResponseDto> findAll(Pageable pageable, LocalDate orderStartDate, LocalDate orderEndDate);

    String addNewOrder(OrderRequestDto request);

    void addProductsToOrder(String orderNumber, OrderRequestDto request);

}
