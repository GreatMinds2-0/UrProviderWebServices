package com.acme.urproviderwebservices.sales.domain.persistence;

import com.acme.urproviderwebservices.sales.domain.model.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    List<SalesOrder> findAllByStatus(String status);
    SalesOrder findSalesOrderById(Long salesOrderId);
    Optional<SalesOrder> findByDescriptionAndStoreId(String description, Long storeId);
}
