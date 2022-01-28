package com.example.product.unit.order;

import com.example.product.core.order.domain.Order;
import com.example.product.core.order.dto.OrderResponseDto;
import com.example.product.core.order.service.OrderMapper;
import com.example.product.core.order.service.impl.OrderMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.List;

@DisplayName("Order Mapper")
@ExtendWith(MockitoExtension.class)
public class OrderMapperTest extends OrderUtil {

    private OrderMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new OrderMapperImpl();
    }

    @DisplayName("entitiesToDtos")
    @Test
    void entitiesToDtos() {
        Page<Order> mock = this.getPageOrderMock();
        List<OrderResponseDto> response = this.mapper.entitiesToDtos(mock);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }
}
