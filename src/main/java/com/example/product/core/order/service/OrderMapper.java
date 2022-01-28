package com.example.product.core.order.service;

import com.example.product.core.order.domain.Order;
import com.example.product.core.order.dto.OrderResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderMapper {

    List<OrderResponseDto> entitiesToDtos(Page<Order> page);
}
