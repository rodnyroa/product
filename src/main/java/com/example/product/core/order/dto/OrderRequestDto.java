package com.example.product.core.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequestDto {

    @JsonIgnore
    private Integer customerId;
    @NotNull
    @NotEmpty
    @Valid
    private List<ItemDto> products;
}
