package com.example.product.unit.order;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.order.domain.Order;
import com.example.product.core.order.dto.ItemDto;
import com.example.product.core.order.dto.OrderRequestDto;
import com.example.product.core.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class OrderUtil {

    protected final String ID = "A";

    protected Page<Order> getPageOrderMock() {
        Order order01 = new Order();
        order01.setOrderTime(LocalDate.now());
        order01.setLineQuantity(1);
        order01.setTotalPriceLine(new BigDecimal(10));
        order01.setLineNumber(1);
        order01.setProductId(1);
        order01.setCustomerId(1);
        order01.setNumber(UUID.randomUUID().toString());

        Product product = new Product();
        product.setId(1);
        product.setName("Test");
        product.setPrice(new BigDecimal(10));

        order01.setProduct(product);

        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Test");
        customer.setLastName("last name");
        customer.setEmail("test@example.com");

        order01.setCustomer(customer);

        List<Order> orders = new ArrayList<>();
        orders.add(order01);

        return new PageImpl<>(orders, PageRequest.of(1, 10), 1);
    }

    protected OrderRequestDto getRequestMock() {
        OrderRequestDto request = new OrderRequestDto();
        request.setCustomerId(1);

        ItemDto item = new ItemDto();
        item.setId(1);
        item.setQuantity(1);

        request.setProducts(Arrays.asList(item));

        return request;
    }
}
