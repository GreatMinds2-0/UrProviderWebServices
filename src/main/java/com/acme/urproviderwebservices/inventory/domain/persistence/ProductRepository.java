package com.acme.urproviderwebservices.inventory.domain.persistence;

import com.acme.urproviderwebservices.inventory.domain.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySupplierId(Long supplierId);
    Page<Product> findBySupplierId(Long supplierId, Pageable pageable);
    Optional<Product> findByIdAndSupplierId(Long id, Long supplierId);

    Optional<Product> findByNameAndSupplierId( String name, Long supplierId);
}
