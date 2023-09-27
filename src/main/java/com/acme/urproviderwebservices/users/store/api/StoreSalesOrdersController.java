package com.acme.urproviderwebservices.users.store.api;

import com.acme.urproviderwebservices.sales.domain.service.SalesOrderService;
import com.acme.urproviderwebservices.sales.mapping.SalesOrderMapper;
import com.acme.urproviderwebservices.sales.resource.CreateSalesOrderResource;
import com.acme.urproviderwebservices.sales.resource.SalesOrderResource;
import com.acme.urproviderwebservices.users.store.domain.service.StoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/stores/{storeId}/salesOrders", produces = "application/json")
@Tag(name = "sales-orders-controller", description = "Create, read, update and delete sales orders by store")
public class StoreSalesOrdersController {

    private final StoreService storeService;

    private final SalesOrderService salesOrderService;

    private final SalesOrderMapper mapper;

    public StoreSalesOrdersController(StoreService storeService, SalesOrderService salesOrderService, SalesOrderMapper mapper) {
        this.storeService = storeService;
        this.salesOrderService = salesOrderService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<SalesOrderResource> getAllSalesOrdersByStoreId(@PathVariable Long storeId) {
        return mapper.modelListPage(storeService.getById(storeId)
                .getSalesOrders().stream().toList());
    }

    @GetMapping("{salesOrderId}")
    public SalesOrderResource getSalesOrderById(@PathVariable Long salesOrderId, @PathVariable Long storeId) {
        return mapper.toResource(salesOrderService.getById(storeId, salesOrderId));
    }

    @PostMapping
    public ResponseEntity <SalesOrderResource> createSalesOrder(@PathVariable Long storeId,
                                                                @Valid @RequestBody CreateSalesOrderResource resource) {
        storeService.addSalesOrderToStore(storeId, mapper.toModel(resource));
        return new ResponseEntity<>(mapper.toResource(salesOrderService.getByDescriptionAndStoreId(mapper.toModel(resource),
                storeId)), HttpStatus.CREATED);
    }

    @PutMapping("{salesOrderId}")
    public SalesOrderResource updateSalesOrder(@PathVariable Long salesOrderId,
                                               @RequestBody CreateSalesOrderResource resource, @PathVariable Long storeId) {
        storeService.updateSalesOrderToStore(storeId, salesOrderId, mapper.toModel(resource));
        return mapper.toResource(salesOrderService.update(salesOrderId, mapper.toModel(resource)));
    }

    @DeleteMapping("{salesOrderId}")
    public ResponseEntity<?> deleteSalesOrder(@PathVariable Long storeId, @PathVariable Long salesOrderId) {
        storeService.deleteSalesOrderToStore(storeId, salesOrderId);
        return salesOrderService.delete(salesOrderId);
    }
}
