package com.example.product.core.customer.domain;

import com.example.product.core.order.domain.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@Table(schema = "public", name = "customer_dim")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_generator")
    @SequenceGenerator(schema = "public", name = "customer_generator", sequenceName = "customer_dim_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firstname")
    @Size(max = 100)
    @NotBlank
    private String firstName;

    @Column(name = "lastname")
    @Size(max = 100)
    @NotBlank
    private String lastName;

    @Column(name = "email")
    @Size(max = 100)
    @NotBlank
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> order;
}
