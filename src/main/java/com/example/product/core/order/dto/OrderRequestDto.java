package com.example.product.core.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequestDto {

    @JsonIgnore
    private Integer customerId;
    private List<ItemDto> products;
}
