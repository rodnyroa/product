package com.example.product.core.order.service;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.customer.service.CustomerSeeker;
import com.example.product.core.order.domain.Order;
import com.example.product.core.product.domain.Product;
import com.example.product.core.product.service.ProductSeeker;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class OrderSeekerFacade {

  private OrderSeeker orderSeeker;
  private ProductSeeker productSeeker;
  private CustomerSeeker customerSeeker;

  @Autowired
  public OrderSeekerFacade(OrderSeeker orderSeeker, ProductSeeker productSeeker,
      CustomerSeeker customerSeeker) {
    this.orderSeeker = orderSeeker;
    this.productSeeker = productSeeker;
    this.customerSeeker = customerSeeker;
  }


  public Page<Order> findAllOrders(Pageable pageable) {
    return this.orderSeeker.findAll(pageable);
  }


  public Page<Order> findAllOrdersBetweenOrderDate(Pageable pageable, LocalDate orderStartDate,
      LocalDate orderEndDate) {
    return this.orderSeeker.findAllOrdersBetweenOrderDate(pageable, orderStartDate, orderEndDate);
  }


  public List<Order> findOrderById(String number) {
    return this.orderSeeker.findById(number);
  }


  public Product findProductById(Integer productId) {
    return this.productSeeker.findById(productId);
  }


  public Customer findCustomerById(Integer customerId) {
    return this.customerSeeker.findById(customerId);
  }

}
