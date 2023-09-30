package com.acme.urproviderwebservices.inventory.api;

import com.acme.urproviderwebservices.inventory.domain.service.ProductService;
import com.acme.urproviderwebservices.inventory.mapping.ProductMapper;
import com.acme.urproviderwebservices.inventory.resource.CreateProductResource;
import com.acme.urproviderwebservices.inventory.resource.ProductResource;
import com.acme.urproviderwebservices.inventory.resource.UpdateProductResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductsController {

    private final ProductService productService;
    private final ProductMapper mapper;

    public ProductsController(ProductService productService, ProductMapper mapper){
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ProductResource> getAllProducts(){
        return mapper.modelListPage(productService.getAll());
    }

    // no es necesaria este endpoint
    
}
