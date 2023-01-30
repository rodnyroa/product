package com.example.product.core.order.service;

import com.example.product.core.exception.NotFoundException;
import com.example.product.core.order.domain.Order;
import com.example.product.core.order.repository.OrderRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class OrderSeeker {

  private OrderRepository repository;

  @Autowired
  public OrderSeeker(OrderRepository repository) {
    this.repository = repository;
  }

  public Page<Order> findAll(Pageable pageable) {

    Page<String> page = this.repository.findGroupByNumber(pageable);
    List<String> orderNumbers = page.getContent();

    List<Order> orders = this.getOrdersByOrderNumber(orderNumbers);

    return new PageImpl<>(orders, pageable, page.getTotalElements());
  }

  public Page<Order> findAllOrdersBetweenOrderDate(Pageable pageable, LocalDate orderStartDate,
      LocalDate orderEndDate) {
    Page<String> page = this.repository.findByOrderTimeBetweenAndGroupByNumber(pageable,
        orderStartDate, orderEndDate);
    List<String> orderNumbers = page.getContent();

    List<Order> orders = this.getOrdersByOrderNumber(orderNumbers);

    return new PageImpl<>(orders, pageable, page.getTotalElements());
  }

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
