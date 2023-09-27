package com.acme.urproviderwebservices.sales.service;

import com.acme.urproviderwebservices.sales.domain.model.entity.SalesOrder;
import com.acme.urproviderwebservices.sales.domain.persistence.SalesOrderRepository;
import com.acme.urproviderwebservices.sales.domain.service.SalesOrderService;
import com.acme.urproviderwebservices.shared.exception.ResourceNotFoundException;
import com.acme.urproviderwebservices.shared.exception.ResourceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    private static final String ENTITY = "SalesOrder";

    private final SalesOrderRepository salesOrderRepository;

    private final Validator validator;

    public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository, Validator validator) {
        this.salesOrderRepository = salesOrderRepository;
        this.validator = validator;
    }

    @Override
    public List<SalesOrder> getAll() {
        return salesOrderRepository.findAll();
    }

    @Override
    public SalesOrder getById(Long salesOrderId) {
        return salesOrderRepository.findById(salesOrderId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, salesOrderId));
    }

    @Override
    public SalesOrder getById(Long storeId, Long salesOrderId) {
        return salesOrderRepository.findById(salesOrderId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, salesOrderId));
    }

    @Override
    public SalesOrder getByDescriptionAndStoreId(SalesOrder salesOrder, Long storeId) {
        return salesOrderRepository.findByDescriptionAndStoreId(salesOrder.getDescription(), storeId)
                .orElseThrow(() -> new ResourceNotFoundException("No Sales Order with this description found for Store"));
    }

    @Override
    public SalesOrder update(Long salesOrderId, SalesOrder request) {
        Set<ConstraintViolation<SalesOrder>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return salesOrderRepository.findById(salesOrderId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, salesOrderId));
    }

    @Override
    public ResponseEntity<?> delete(Long salesOrderId) {
        return salesOrderRepository.findById(salesOrderId).map(salesOrder -> {
            salesOrderRepository.delete(salesOrder);
            return ResponseEntity.ok().build(); })
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, salesOrderId));
    }
}
