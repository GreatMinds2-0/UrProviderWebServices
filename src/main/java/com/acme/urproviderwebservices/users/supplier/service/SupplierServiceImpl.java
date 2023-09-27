package com.acme.urproviderwebservices.users.supplier.service;

import com.acme.urproviderwebservices.inventory.domain.model.entity.Product;
import com.acme.urproviderwebservices.sales.domain.model.entity.Review;
import com.acme.urproviderwebservices.shared.exception.ResourceNotFoundException;
import com.acme.urproviderwebservices.shared.exception.ResourceValidationException;
import com.acme.urproviderwebservices.users.supplier.domain.model.entity.Supplier;
import com.acme.urproviderwebservices.users.supplier.domain.persistence.SupplierRepository;
import com.acme.urproviderwebservices.users.supplier.domain.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
public class SupplierServiceImpl implements SupplierService {
    private static final String ENTITY = "Supplier";
    private final SupplierRepository supplierRepository;
    private final Validator validator;

    public SupplierServiceImpl(SupplierRepository supplierRepository, Validator validator ) {
        this.supplierRepository = supplierRepository;
        this.validator = validator;
    }

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Page<Supplier> getAll(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    @Override
    public Supplier getById(Long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, supplierId));
    }

    @Override
    public Supplier create(Supplier supplier) {
        Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        // Email Uniqueness validation
        Optional<Supplier> supplierWithEmail = supplierRepository.findByEmail(supplier.getEmail());
        if (supplierWithEmail.isPresent()){
            throw new ResourceValidationException(ENTITY,
                    "A supplier with the same email already exist.");
        }


        return supplierRepository.save(new Supplier()
                .withSupplierName(supplier.getSupplierName())
                .withName(supplier.getName())
                .withLastName(supplier.getLastName())
                .withImage(supplier.getImage())
                .withEmail(supplier.getEmail())
                .withAddress(supplier.getAddress())
                .withRuc(supplier.getRuc())
                .withCategory(supplier.getCategory())
                .withDescription(supplier.getDescription())
                .withPhone(supplier.getPhone())
                .withPassword(supplier.getPassword())
        ) ;
    }

    @Override
    public Supplier update(Long supplierId, Supplier request) {
        Set<ConstraintViolation<Supplier>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        // Name Uniqueness validation
        Optional<Supplier> supplierWithEmail = supplierRepository.findByEmail(request.getEmail());

        if(supplierWithEmail.isPresent() && !supplierWithEmail.get().getId().equals(supplierId))
            throw new ResourceValidationException(ENTITY,
                    "A supplier with the same email already exists.");

        return supplierRepository.findById(supplierId).map(existingSupplier ->
                        supplierRepository.save(existingSupplier.withSupplierName(request.getSupplierName())
                                        .withName(request.getName())
                                        .withLastName(request.getLastName())
                                        .withImage(request.getImage())
                                        .withEmail(request.getEmail())
                                        .withAddress(request.getAddress())
                                        .withRuc(request.getRuc())
                                        .withCategory(request.getCategory())
                                        .withDescription(request.getDescription())
                                        .withPhone(request.getPhone())
                                        .withPassword(request.getPassword())
                                        .withLikes(request.getLikes())
                                        ))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, supplierId));
    }

    @Override
    public ResponseEntity<?> delete(Long supplierId) {

        return supplierRepository.findById(supplierId).map(supplier -> {
            supplierRepository.delete(supplier);
            return ResponseEntity.ok().build(); })
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, supplierId));

    }

    @Override
    public Supplier updateProductToSupplier(Long supplierId, Long productId, Product product){
        return supplierRepository.findById(supplierId).map(supplier ->
                        supplierRepository.save(supplier.updateProduct(product,productId)))
                .orElseThrow(()->new ResourceNotFoundException(ENTITY, supplierId));
    }
    @Override
    public Supplier addProductToSupplier(Long supplierId, Product productName) {
        return supplierRepository.findById(supplierId).map(supplier ->
                        supplierRepository.save(supplier.addProduct(productName)))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, supplierId));
    }

    @Override
    public Supplier deleteProductToSupplier(Long supplierId,Long productId){
        return supplierRepository.findById(supplierId).map(supplier ->
                        supplierRepository.save(supplier.deleteProduct(productId)))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, supplierId));
    }
    @Override
    public Supplier updateReviewToSupplier(Long supplierId, Long reviewId, Review review){
        return supplierRepository.findById(supplierId).map(supplier ->
                supplierRepository.save(supplier.updateReview(review,reviewId)))
                .orElseThrow(()->new ResourceNotFoundException(ENTITY, supplierId));
    }
    @Override
    public Supplier addReviewToSupplier(Long supplierId, Review reviewTitle) {
        return supplierRepository.findById(supplierId).map(supplier ->
                        supplierRepository.save(supplier.addReview(reviewTitle)))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, supplierId));
    }

    @Override
    public Supplier deleteReviewToSupplier(Long supplierId,Long reviewId){
        return supplierRepository.findById(supplierId).map(supplier ->
                        supplierRepository.save(supplier.deleteReview(reviewId)))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, supplierId));
    }

}
