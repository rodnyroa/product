package com.example.product.core.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPK implements Serializable {

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "order_time")
    private LocalDate orderTime;

    @Column(name = "order_number")
    private String number;
}
