package com.example.product.core.product.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@Table(schema = "public", name = "product_dim")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(schema = "public", name = "product_generator", sequenceName = "product_dim_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @Size(max = 100)
    @NotBlank
    private String name;

    @Column(name = "price")
    @NotNull
    private BigDecimal price;

}
