package com.example.product.core.order.service.impl;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.customer.service.CustomerSeeker;
import com.example.product.core.order.domain.Order;
import com.example.product.core.order.service.OrderSeeker;
import com.example.product.core.order.service.OrderSeekerFacade;
import com.example.product.core.product.domain.Product;
import com.example.product.core.product.service.ProductSeeker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class OrderSeekerFacadeImpl implements OrderSeekerFacade {

    private OrderSeeker orderSeeker;
    private ProductSeeker productSeeker;
    private CustomerSeeker customerSeeker;

    @Autowired
    public OrderSeekerFacadeImpl(OrderSeeker orderSeeker, ProductSeeker productSeeker, CustomerSeeker customerSeeker) {
        this.orderSeeker = orderSeeker;
        this.productSeeker = productSeeker;
        this.customerSeeker = customerSeeker;
    }

    @Override
    public Page<Order> findAllOrders(Pageable pageable) {
        return this.orderSeeker.findAll(pageable);
    }

    @Override
    public Page<Order> findAllOrdersBetweenOrderDate(Pageable pageable, LocalDate orderStartDate, LocalDate orderEndDate) {
        return this.orderSeeker.findAllOrdersBetweenOrderDate(pageable, orderStartDate, orderEndDate);
    }

    @Override
    public List<Order> findOrderById(String number) {
        return this.orderSeeker.findById(number);
    }

    @Override
    public Product findProductById(Integer productId) {
        return this.productSeeker.findById(productId);
    }

    @Override
    public Customer findCustomerById(Integer customerId) {
        return this.customerSeeker.findById(customerId);
    }

}
