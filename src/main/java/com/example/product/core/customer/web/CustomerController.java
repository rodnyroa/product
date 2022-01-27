package com.example.product.core.customer.web;

import com.example.product.core.customer.dto.CustomerDto;
import com.example.product.core.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/customers")
public class CustomerController {

    private CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @Operation(summary = "Get customers")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "customer list",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))
                    }
            )
    })
    @GetMapping
    public ResponseEntity<Page<CustomerDto>> findAll(Pageable pageable) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(this.service.findAll(pageable), headers, org.springframework.http.HttpStatus.OK);
    }
}
