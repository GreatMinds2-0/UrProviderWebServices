package com.acme.urproviderwebservices.sales.domain.service;

import com.acme.urproviderwebservices.sales.domain.model.entity.SalesOrder;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SalesOrderService {
    List<SalesOrder> getAll();
    SalesOrder getById(Long salesOrderId);
    SalesOrder getById(Long storeId, Long salesOrderId);
    SalesOrder getByDescriptionAndStoreId(SalesOrder name,Long id);
    SalesOrder update(Long salesOrderId, SalesOrder request);
    ResponseEntity<?> delete(Long salesOrderId);
}
