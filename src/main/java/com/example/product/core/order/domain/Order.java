package com.example.product.core.order.domain;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.product.domain.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@Table(schema = "public", name = "order_fact")
@IdClass(OrderPK.class)
public class Order implements Serializable {

    @Id
    @Column(name = "customer_id")
    private Integer customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @Id
    @Column(name = "product_id")
    private Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Id
    @NotNull
    @Column(name = "order_time", columnDefinition = "date")
    private LocalDate orderTime;

    @Id
    @NotNull
    @Column(name = "order_number")
    private String number;

    @NotNull
    @Column(name = "order_line_number")
    private Integer lineNumber;

    @NotNull
    @Column(name = "order_line_quantity")
    private Integer lineQuantity;

    @NotNull
    @Column(name = "total_price_line")
    private BigDecimal totalPriceLine;

}
