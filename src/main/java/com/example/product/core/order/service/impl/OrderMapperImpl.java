package com.example.product.core.order.service.impl;

import com.example.product.core.order.domain.Order;
import com.example.product.core.order.dto.ItemDto;
import com.example.product.core.order.dto.OrderResponseDto;
import com.example.product.core.order.service.OrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public List<OrderResponseDto> entitiesToDtos(Page<Order> page) {

        List<OrderResponseDto> response = new ArrayList<>();

        Map<String, List<Order>> ordersPerOrderNumber = page.getContent()
                .stream()
                .collect(Collectors.groupingBy(Order::getNumber));

        ordersPerOrderNumber.forEach((orderNumber, orders) -> this.buildOrderDto(response, orderNumber, orders));
        return response;
    }

    private void buildOrderDto(List<OrderResponseDto> response, String orderNumber, List<Order> orders) {

        OrderResponseDto orderDto = new OrderResponseDto();
        orderDto.setOrderNumber(orderNumber);

        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(new BigDecimal(0));

        List<ItemDto> items = new ArrayList<>();

        orders.stream()
                .forEach(order -> {

                    ItemDto item = this.buildItemDto(order);
                    orderDto.setBuyer(order.getCustomer().getEmail());
                    orderDto.setOrderTime(order.getOrderTime());
                    totalAmount.updateAndGet(total -> total.add(order.getTotalPriceLine()));
                    items.add(item);
                });
        orderDto.setTotalAmount(totalAmount.get());
        orderDto.setProducts(items);

        response.add(orderDto);
    }

    private ItemDto buildItemDto(Order order) {
        return ItemDto.builder()
                .name(order.getProduct().getName())
                .quantity(order.getLineQuantity())
                .price(order.getTotalPriceLine().divide(new BigDecimal(order.getLineQuantity())))
                .build();
    }
}
