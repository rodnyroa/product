package com.example.product.core.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderResponseDto {

    @JsonProperty("order_number")
    private String orderNumber;

    @JsonProperty("buyer_email")
    private String buyer;

    @JsonProperty("order_time")
    private LocalDate orderTime;

    @JsonProperty("products")
    private List<ItemDto> products;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

}
