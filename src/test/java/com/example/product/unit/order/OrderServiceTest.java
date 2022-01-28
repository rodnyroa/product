package com.example.product.unit.order;

import com.example.product.core.exception.NotFoundException;
import com.example.product.core.order.dto.OrderRequestDto;
import com.example.product.core.order.dto.OrderResponseDto;
import com.example.product.core.order.repository.OrderRepository;
import com.example.product.core.order.service.OrderSeekerFacade;
import com.example.product.core.order.service.OrderService;
import com.example.product.core.order.service.impl.OrderMapperImpl;
import com.example.product.core.order.service.impl.OrderServiceImpl;
import com.example.product.core.product.domain.Product;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

@DisplayName("Order Service")
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest extends OrderUtil {

    private final static LocalDate NO_DATE = null;

    @Mock
    private OrderSeekerFacade seeker;

    @Mock
    private OrderRepository repository;

    private InOrder inOrder;
    private OrderService service;

    @BeforeEach
    void setup() {
        this.service = new OrderServiceImpl(this.seeker, new OrderMapperImpl(), this.repository);
        this.inOrder = Mockito.inOrder(this.seeker, this.repository);
    }

    @DisplayName("findAll without order dates")
    @Test
    void findAll01() {

        Mockito.when(this.seeker.findAllOrders(Mockito.any(PageRequest.class))).thenReturn(this.getPageOrderMock());

        Page<OrderResponseDto> page = this.service.findAll(PageRequest.of(1, 10), NO_DATE, NO_DATE);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.getContent().isEmpty());

        this.inOrder.verify(this.seeker).findAllOrders(Mockito.any(PageRequest.class));

        this.inOrder.verifyNoMoreInteractions();

    }

    @DisplayName("findAll only with order start date")
    @Test
    void findAll02() {

        Mockito.when(this.seeker.findAllOrdersBetweenOrderDate(Mockito.any(PageRequest.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class))).thenReturn(this.getPageOrderMock());

        Page<OrderResponseDto> page = this.service.findAll(PageRequest.of(1, 10), LocalDate.now(), NO_DATE);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.getContent().isEmpty());

        this.inOrder.verify(this.seeker).findAllOrdersBetweenOrderDate(Mockito.any(PageRequest.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class));

        this.inOrder.verifyNoMoreInteractions();

    }

    @DisplayName("findAll with order start date and end date")
    @Test
    void findAll03() {

        Mockito.when(this.seeker.findAllOrdersBetweenOrderDate(Mockito.any(PageRequest.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class))).thenReturn(this.getPageOrderMock());

        Page<OrderResponseDto> page = this.service.findAll(PageRequest.of(1, 10), LocalDate.now(), LocalDate.now());

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.getContent().isEmpty());

        this.inOrder.verify(this.seeker).findAllOrdersBetweenOrderDate(Mockito.any(PageRequest.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class));

        this.inOrder.verifyNoMoreInteractions();

    }

    @DisplayName("addNewOrder successful")
    @Test
    void addNewOrder() {

        Product productMock = new Product();
        productMock.setPrice(new BigDecimal(10));

        Mockito.when(this.seeker.findProductById(Mockito.anyInt())).thenReturn(productMock);

        Mockito.when(this.repository.saveAll(Mockito.anyCollection())).thenReturn(Collections.emptyList());

        String orderNumber = this.service.addNewOrder(this.getRequestMock());

        Assertions.assertNotNull(orderNumber);
        Assertions.assertTrue(orderNumber.length() > 0);

        this.inOrder.verify(this.seeker).findProductById(Mockito.anyInt());
        this.inOrder.verify(this.repository).saveAll(Mockito.anyCollection());
        this.inOrder.verifyNoMoreInteractions();
    }

    @DisplayName("addProductsToOrder successful")
    @Test
    void addProductsToOrder() {

        Mockito.when(this.seeker.findOrderById(Mockito.anyString())).thenReturn(this.getPageOrderMock().getContent());

        Mockito.when(this.repository.saveAll(Mockito.anyCollection())).thenReturn(Collections.emptyList());

        Product productMock = new Product();
        productMock.setPrice(new BigDecimal(10));
        productMock.setId(1);

        Mockito.when(this.seeker.findProductById(Mockito.anyInt())).thenReturn(productMock);

        String orderNumber = "123";
        this.service.addProductsToOrder(orderNumber, this.getRequestMock());


        this.inOrder.verify(this.seeker).findOrderById(Mockito.anyString());
        this.inOrder.verify(this.repository).saveAll(Mockito.anyCollection());
        this.inOrder.verifyNoMoreInteractions();
    }

    @DisplayName("addProductsToOrder throw NotFoundException with invalid customer id")
    @Test
    void addProductsToOrder01() {

        Mockito.when(this.seeker.findOrderById(Mockito.anyString())).thenReturn(this.getPageOrderMock().getContent());

        String orderNumber = "123";
        OrderRequestDto request = this.getRequestMock();
        request.setCustomerId(99);

        Assertions.assertThrows(NotFoundException.class, () -> this.service.addProductsToOrder(orderNumber, request));

        this.inOrder.verify(this.seeker).findOrderById(Mockito.anyString());
        this.inOrder.verifyNoMoreInteractions();
    }
}
