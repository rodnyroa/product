package com.example.product.core.order.web;

import com.example.product.config.controller.BaseControllerException;
import com.example.product.core.order.dto.OrderRequestDto;
import com.example.product.core.order.dto.OrderResponseDto;
import com.example.product.core.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/v1/")
public class OrderController extends BaseControllerException {

    private OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }


    @Operation(summary = "Create an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderRequestDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content)
    })
    @PostMapping(value = "customers/{customer-id}/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> addNewOrder(
            @PathVariable("customer-id") Integer customerId,
            @Valid @RequestBody OrderRequestDto request) {
        request.setCustomerId(customerId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(service.addNewOrder(request)).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Add products to created order ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderRequestDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content)
    })
    @PostMapping(value = "customers/{customer-id}/orders/{order-number}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> addProductsToOrder(
            @PathVariable("customer-id") Integer customerId,
            @PathVariable("order-number") String orderNumber,
            @Valid @RequestBody OrderRequestDto request) {
        request.setCustomerId(customerId);
        this.service.addProductsToOrder(orderNumber, request);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Get orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order list",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class))}),
    })
    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> findAllOrders(
            @RequestParam(name = "order-start-date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate orderStartDate,
            @RequestParam(name = "order-end-date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate orderEndDate,
            Pageable pageable
    ) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(this.service.findAll(pageable, orderStartDate, orderEndDate), headers, HttpStatus.OK);
    }

}
