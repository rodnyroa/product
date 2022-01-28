package com.example.product.unit.order;

import com.example.product.core.exception.NotFoundException;
import com.example.product.core.order.domain.Order;
import com.example.product.core.order.repository.OrderRepository;
import com.example.product.core.order.service.OrderSeeker;
import com.example.product.core.order.service.impl.OrderSeekerImpl;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@DisplayName("Order Seeker")
@ExtendWith(MockitoExtension.class)
public class OrderSeekerTests extends OrderUtil {

    private OrderSeeker seeker;

    @Mock
    private OrderRepository repository;

    private InOrder inOrder;

    @BeforeEach
    void setup() {
        this.seeker = new OrderSeekerImpl(this.repository);
        this.inOrder = Mockito.inOrder(this.repository);
    }

    @DisplayName("findById returns value")
    @Test
    void findById() {
        List<Order> mock = this.getPageOrderMock().getContent();
        Mockito.when(this.repository.findByNumber(Mockito.anyString()))
                .thenReturn(mock);
        List<Order> orders = this.seeker.findById("A");
        Assertions.assertNotNull(orders);
        Assertions.assertFalse(orders.isEmpty());

    }

    @DisplayName("when findById with invalid id should throw NotFoundException")
    @Test
    void findByIdShouldThrowException() {
        Mockito.when(this.repository.findByNumber(Mockito.anyString()))
                .thenReturn(Collections.emptyList());

        Assertions.assertThrows(NotFoundException.class, () -> this.seeker.findById(ID));
    }

    @DisplayName("when call findAll with order dates then return items")
    @Test
    void findAllWithOrderDates() {

        Page<String> mock = new PageImpl<>(Arrays.asList("A", "B"));
        Mockito.when(this.repository.findByOrderTimeBetweenAndGroupByNumber(Mockito.any(PageRequest.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                .thenReturn(mock);
        Mockito.when(this.repository.findByNumberIn(Mockito.anyList())).thenReturn(this.getPageOrderMock().getContent());

        Page<Order> page = this.seeker.findAllOrdersBetweenOrderDate(PageRequest.of(1, 10), LocalDate.now(), LocalDate.now());
        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.getContent().isEmpty());

        this.inOrder.verify(this.repository).findByOrderTimeBetweenAndGroupByNumber(Mockito.any(PageRequest.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class));
        this.inOrder.verify(this.repository).findByNumberIn(Mockito.anyList());

        this.inOrder.verifyNoMoreInteractions();
    }

    @DisplayName("findAll with order dates with no page request")
    @Test
    void findAllWithOrderDates01() {
        Page<String> mock = new PageImpl<>(Collections.emptyList());
        Mockito.when(this.repository.findByOrderTimeBetweenAndGroupByNumber(Mockito.any(PageRequest.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                .thenReturn(mock);

        Mockito.when(this.repository.findByNumberIn(Mockito.anyList())).thenReturn(Collections.emptyList());

        Page<Order> page = this.seeker.findAllOrdersBetweenOrderDate(PageRequest.of(1, 10), LocalDate.now(), LocalDate.now());

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.hasContent());

        this.inOrder.verify(this.repository).findByOrderTimeBetweenAndGroupByNumber(Mockito.any(PageRequest.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class));
        this.inOrder.verify(this.repository).findByNumberIn(Mockito.anyList());

        this.inOrder.verifyNoMoreInteractions();
    }

    @DisplayName("when call findAll then return items")
    @Test
    void findAll() {

        Page<String> mock = new PageImpl<>(Arrays.asList("A", "B"));
        Mockito.when(this.repository.findGroupByNumber(Mockito.any(PageRequest.class)))
                .thenReturn(mock);
        Mockito.when(this.repository.findByNumberIn(Mockito.anyList())).thenReturn(this.getPageOrderMock().getContent());
        Page<Order> page = this.seeker.findAll(PageRequest.of(1, 10));
        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.getContent().isEmpty());

        this.inOrder.verify(this.repository).findGroupByNumber(Mockito.any(PageRequest.class));
        this.inOrder.verify(this.repository).findByNumberIn(Mockito.anyList());

        this.inOrder.verifyNoMoreInteractions();

    }

    @DisplayName("findAll with no page request")
    @Test
    void findAll01() {
        Page<String> mock = new PageImpl<>(Collections.emptyList());
        Mockito.when(this.repository.findGroupByNumber(Mockito.any(PageRequest.class)))
                .thenReturn(mock);

        Page<Order> page = this.seeker.findAll(PageRequest.of(1, 10));

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.hasContent());

        this.inOrder.verify(this.repository).findGroupByNumber(Mockito.any(PageRequest.class));
        this.inOrder.verify(this.repository).findByNumberIn(Mockito.anyList());

        this.inOrder.verifyNoMoreInteractions();
    }

}
