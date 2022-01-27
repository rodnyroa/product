package com.example.product.core.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDto implements Serializable {

    private Integer id;

    @NotBlank(message = "Is mandatory")
    private String name;
    @NotNull(message = "Is mandatory")
    private BigDecimal price;
}
