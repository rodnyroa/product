package com.example.product.core.order.repository;

import com.example.product.core.order.domain.Order;
import com.example.product.core.order.domain.OrderPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, OrderPK> {

    List<Order> findByNumber(String number);

    @Query("select o.number from Order as o group by o.number")
    Page<String> findGroupByNumber(Pageable pageable);

    @Query("select o.number from Order as o where o.orderTime between :start and :end group by o.number")
    Page<String> findByOrderTimeBetweenAndGroupByNumber(Pageable pageable,
                                                        @Param("start") LocalDate start,
                                                        @Param("end") LocalDate end);

    List<Order> findByNumberIn(List<String> numbers);
}