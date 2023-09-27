package com.acme.urproviderwebservices.inventory.service;

import com.acme.urproviderwebservices.inventory.domain.model.entity.Product;
import com.acme.urproviderwebservices.inventory.domain.persistence.ProductRepository;
import com.acme.urproviderwebservices.inventory.domain.service.ProductService;
import com.acme.urproviderwebservices.shared.exception.ResourceNotFoundException;
import com.acme.urproviderwebservices.shared.exception.ResourceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

   private static final String ENTITY = "Product";
   private final ProductRepository productRepository;
    private final Validator validator;
    public ProductServiceImpl(ProductRepository productRepository, Validator validator) {
        this.productRepository = productRepository;
        this.validator = validator;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, productId));
    }

    @Override
    public Product getById(Long supplierId,Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, productId));
    }


    //post
    @Override
    public Product getByNameAndSupplierId(Product product, Long supplierId){
        return productRepository.findByNameAndSupplierId(product.getName(), supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("No Product with this name found for Supplier"));
    }

    @Override
    public Product update(Long productId, Product request) {
        Set<ConstraintViolation<Product>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, productId));
    }



    @Override
    public ResponseEntity<?> delete(Long productId) {
        return productRepository.findById(productId).map(product -> {
                    productRepository.delete(product);
                    return ResponseEntity.ok().build(); })
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, productId));
    }
}
