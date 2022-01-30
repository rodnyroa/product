package com.example.product.unit.order;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.customer.service.CustomerSeeker;
import com.example.product.core.order.domain.Order;
import com.example.product.core.order.service.OrderSeeker;
import com.example.product.core.order.service.OrderSeekerFacade;
import com.example.product.core.order.service.impl.OrderSeekerFacadeImpl;
import com.example.product.core.product.domain.Product;
import com.example.product.core.product.service.ProductSeeker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

@DisplayName("Order Seeker Facade")
@ExtendWith(MockitoExtension.class)
public class OrderSeekerFacadeTest extends OrderUtil {

    private OrderSeekerFacade seeker;

    @Mock
    private OrderSeeker orderSeeker;
    @Mock
    private ProductSeeker productSeeker;
    @Mock
    private CustomerSeeker customerSeeker;

    private InOrder inOrder;

    @BeforeEach
    void setup() {
        this.seeker = new OrderSeekerFacadeImpl(this.orderSeeker, productSeeker, this.customerSeeker);
        this.inOrder = Mockito.inOrder(this.orderSeeker, productSeeker, this.customerSeeker);
    }

    @DisplayName("findAllOrders successful")
    @Test
    void findById() {

        Mockito.when(this.orderSeeker.findAll(Mockito.any(PageRequest.class))).thenReturn(Page.empty());

        Page<Order> page = this.seeker.findAllOrders(PageRequest.of(1, 10));

        Assertions.assertNotNull(page);

        this.inOrder.verify(this.orderSeeker).findAll(Mockito.any(PageRequest.class));
        this.inOrder.verifyNoMoreInteractions();

    }

    @DisplayName("findAllOrdersBetweenOrderDate successful")
    @Test
    void findAllOrdersBetweenOrderDate() {

        Mockito.when(this.orderSeeker.findAllOrdersBetweenOrderDate(Mockito.any(PageRequest.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class))).thenReturn(Page.empty());

        Page<Order> page = this.seeker.findAllOrdersBetweenOrderDate(PageRequest.of(1, 10), LocalDate.now(), LocalDate.now());

        Assertions.assertNotNull(page);

        this.inOrder.verify(this.orderSeeker).findAllOrdersBetweenOrderDate(Mockito.any(PageRequest.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class));
        this.inOrder.verifyNoMoreInteractions();
    }

    @DisplayName("findOrderById successful")
    @Test
    void findOrderById() {

        Mockito.when(this.orderSeeker.findById(Mockito.anyString())).thenReturn(this.getPageOrderMock().getContent());

        List<Order> orders = this.seeker.findOrderById("A");

        Assertions.assertNotNull(orders);
        Assertions.assertFalse(orders.isEmpty());

        this.inOrder.verify(this.orderSeeker).findById(Mockito.anyString());
        this.inOrder.verifyNoMoreInteractions();
    }

    @DisplayName("findProductById successful")
    @Test
    void findProductById() {

        Mockito.when(this.productSeeker.findById(Mockito.anyInt())).thenReturn(new Product());

        Product product = this.seeker.findProductById(1);

        Assertions.assertNotNull(product);

        this.inOrder.verify(this.productSeeker).findById(Mockito.anyInt());
        this.inOrder.verifyNoMoreInteractions();

    }

    @DisplayName("findCustomerById successful")
    @Test
    void asdasda() {

        Mockito.when(this.customerSeeker.findById(Mockito.anyInt())).thenReturn(new Customer());

        Customer customer = this.seeker.findCustomerById(1);

        Assertions.assertNotNull(customer);

        this.inOrder.verify(this.customerSeeker).findById(Mockito.anyInt());
        this.inOrder.verifyNoMoreInteractions();

    }
}
