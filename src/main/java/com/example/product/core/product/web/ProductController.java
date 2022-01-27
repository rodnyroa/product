package com.example.product.core.product.web;

import com.example.product.config.controller.BaseControllerException;
import com.example.product.core.product.dto.ProductDto;
import com.example.product.core.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/v1/products")
public class ProductController extends BaseControllerException {

    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Get products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products list",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class))}),
    })
    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(Pageable pageable) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(this.service.findAll(pageable), headers, org.springframework.http.HttpStatus.OK);
    }

    @Operation(summary = "Get a product by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Found products",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404", description = "Product not found",
                    content = @Content)
    }
    )
    @GetMapping("/{product-id}")
    public ResponseEntity<ProductDto> findById(@PathVariable("product-id") Integer productId) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(this.service.findById(productId), headers, HttpStatus.OK);
    }

    @Operation(summary = "Update a product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found products",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)})
    @PutMapping(path = "/{product-id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> update(
            @PathVariable("product-id") Integer productId,
            @Valid @RequestBody ProductDto request) {
        service.update(productId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> add(
            @Valid @RequestBody ProductDto request) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(service.add(request)).toUri();

        return ResponseEntity.created(location).build();
    }
}
