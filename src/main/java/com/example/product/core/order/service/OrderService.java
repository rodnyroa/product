package com.example.product.core.order.service;

import com.example.product.core.exception.NotFoundException;
import com.example.product.core.order.domain.Order;
import com.example.product.core.order.dto.ItemDto;
import com.example.product.core.order.dto.OrderRequestDto;
import com.example.product.core.order.dto.OrderResponseDto;
import com.example.product.core.order.repository.OrderRepository;
import com.example.product.core.product.domain.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private OrderSeekerFacade seeker;
  private OrderMapper mapper;
  private OrderRepository repository;

  @Autowired
  public OrderService(OrderSeekerFacade seeker, OrderMapper mapper, OrderRepository repository) {
    this.seeker = seeker;
    this.mapper = mapper;
    this.repository = repository;
  }


  public Page<OrderResponseDto> findAll(Pageable pageable, LocalDate orderStartDate,
      LocalDate orderEndDate) {
    Page<Order> page;
    if (this.hasOrderStartAndEndDate(orderStartDate, orderEndDate) || Objects.isNull(
        orderStartDate)) {
      page = this.seeker.findAllOrders(pageable);
    } else if (Objects.isNull(orderEndDate)) {
      orderEndDate = orderStartDate;
      page = this.seeker.findAllOrdersBetweenOrderDate(pageable, orderStartDate, orderEndDate);
    } else {
      page = this.seeker.findAllOrdersBetweenOrderDate(pageable, orderStartDate, orderEndDate);
    }

    List<OrderResponseDto> response = this.mapper.entitiesToDtos(page);

    return new PageImpl<>(response, pageable, page.getTotalElements());
  }


  public String addNewOrder(OrderRequestDto request) {
    List<Order> orders = new ArrayList<>();

    this.checkCustomer(request.getCustomerId());

    String orderNumber = UUID.randomUUID().toString();
    AtomicInteger lineNumber = new AtomicInteger(1);
    request.getProducts()
        .parallelStream()
        .forEach(item -> {
          Order order = this.manageOrder(request, lineNumber, item, orderNumber);
          orders.add(order);
        });

    this.saveAll(orders);

    return orderNumber;
  }


  public void addProductsToOrder(String orderNumber, OrderRequestDto request) {
    List<Order> orders = this.seeker.findOrderById(orderNumber);
    Order orderRegistered = orders.get(0);

    if (!request.getCustomerId().equals(orderRegistered.getCustomerId())) {
      throw new NotFoundException("Invalid customer id");
    }

    AtomicInteger lineNumber = new AtomicInteger(orders.size() + 1);
    List<Order> newOrders = new ArrayList<>();
    request.getProducts()
        .parallelStream()
        .forEach(item -> {
          Order order = this.manageOrder(request, lineNumber, item, orderNumber);
          order.setOrderTime(orderRegistered.getOrderTime());
          newOrders.add(order);
        });

    this.saveAll(newOrders);
  }

  private Order manageOrder(OrderRequestDto request, AtomicInteger lineNumber, ItemDto productDto,
      String orderNumber) {
    Order order = new Order();
    order.setCustomerId(request.getCustomerId());
    Product product = this.getProductInformation(productDto);
    order.setProductId(product.getId());
    order.setOrderTime(LocalDate.now());
    order.setNumber(orderNumber);
    order.setLineNumber(lineNumber.getAndIncrement());
    Integer lineQuantity = 1;
    if (Objects.nonNull(productDto.getQuantity())) {
      lineQuantity = productDto.getQuantity();
    }
    order.setLineQuantity(lineQuantity);
    order.setTotalPriceLine(new BigDecimal(lineQuantity).multiply(product.getPrice()));
    return order;
  }


  private Product getProductInformation(ItemDto productDto) {
    return this.seeker.findProductById(productDto.getId());
  }

  private void saveAll(List<Order> orders) {
    this.repository.saveAll(orders);
  }

  private boolean hasOrderStartAndEndDate(LocalDate orderStartDate, LocalDate orderEndDate) {
    return Objects.isNull(orderStartDate) && Objects.isNull(orderEndDate);
  }

  private void checkCustomer(Integer customerId) {
    this.seeker.findCustomerById(customerId);
  }
}
