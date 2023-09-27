package com.acme.urproviderwebservices.users.supplier.api;

import com.acme.urproviderwebservices.inventory.domain.service.ProductService;
import com.acme.urproviderwebservices.inventory.mapping.ProductMapper;
import com.acme.urproviderwebservices.inventory.resource.CreateProductResource;
import com.acme.urproviderwebservices.inventory.resource.ProductResource;
import com.acme.urproviderwebservices.users.supplier.domain.service.SupplierService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/suppliers/{supplierId}/products", produces = "application/json")
@Tag(name = "products-controller", description = "Create, read, update and delete products by supplier")
public class SupplierProductsController {

    private final SupplierService supplierService;

    private final ProductService productService;

    private final ProductMapper mapper;

    public SupplierProductsController(SupplierService supplierService, ProductService productService, ProductMapper mapper) {
        this.supplierService = supplierService;
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ProductResource> getAllProductsBySupplierId(@PathVariable Long supplierId) {
        return mapper.modelListPage(supplierService.getById(supplierId)
                .getProducts().stream().toList());
    }
    @GetMapping("{productId}")
    public ProductResource getProductById(@PathVariable Long productId, @PathVariable Long supplierId) {
        return mapper.toResource(productService.getById(supplierId,productId));
    }

    @PostMapping
    public ResponseEntity <ProductResource> createProduct(@PathVariable Long supplierId,
                                                          @Valid  @RequestBody CreateProductResource resource) {
        // product name Uniqueness validation
        supplierService.addProductToSupplier(supplierId,mapper.toModel(resource));
        //publish product
        return new ResponseEntity<>(mapper.toResource(productService.getByNameAndSupplierId(mapper.toModel(resource),
                        supplierId)), HttpStatus.CREATED);
    }

    @PutMapping("{productId}")
    public ProductResource updateProduct(@PathVariable Long productId,
                                         @RequestBody CreateProductResource resource, @PathVariable Long supplierId) {
        supplierService.updateProductToSupplier(supplierId,productId,mapper.toModel(resource));
        return mapper.toResource(productService.update(productId,mapper.toModel(resource)));
    }
    @DeleteMapping("{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long supplierId, @PathVariable Long productId){
        supplierService.deleteProductToSupplier(supplierId,productId);
        return productService.delete(productId);
    }






}
