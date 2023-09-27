package com.acme.urproviderwebservices.users.store.service;

import com.acme.urproviderwebservices.sales.domain.model.entity.SalesOrder;
import com.acme.urproviderwebservices.shared.exception.ResourceNotFoundException;
import com.acme.urproviderwebservices.shared.exception.ResourceValidationException;
import com.acme.urproviderwebservices.users.store.domain.model.entity.Store;
import com.acme.urproviderwebservices.users.store.domain.persistence.StoreRepository;
import com.acme.urproviderwebservices.users.store.domain.service.StoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class StoreServiceImpl implements StoreService {
    private static final String ENTITY = "Store";

    private final StoreRepository storeRepository;

    private final Validator validator;

    public StoreServiceImpl(StoreRepository storeRepository, Validator validator) {
        this.storeRepository=storeRepository;
        this.validator= validator;

    }
    @Override
    public List<Store> getAll() {return storeRepository.findAll();}

    @Override
    public Page<Store> getAll(Pageable pageable){return storeRepository.findAll(pageable);}

    @Override
    public Store getById(Long storeId){
        return storeRepository.findById(storeId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, storeId));
    }

    @Override
    public Store create(Store store){
        Set<ConstraintViolation<Store>> violations = validator.validate(store);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);
        // Email Uniqueness validation
        Store storeWithEmail = storeRepository.findByEmail(store.getEmail());
        if (storeWithEmail != null)
            throw new ResourceValidationException(ENTITY,
                    "A store with the same email already exist.");

        return storeRepository.save(store);
    }

    @Override
    public Store update(Long storeId, Store request){
        Set<ConstraintViolation<Store>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY,violations);
        // Email Uniqueness validation
        Store storeWithEmail = storeRepository.findByEmail(request.getEmail());
        if (storeWithEmail != null && !storeWithEmail.getId().equals(storeId))
            throw new ResourceValidationException(ENTITY,
                    "A store with the same email already exists.");
        return storeRepository.findById(storeId).map(existingStore ->
                storeRepository.save(
                        existingStore.withStoreName(request.getStoreName())
                                .withName(request.getName())
                                .withLastName(request.getLastName())
                                .withEmail(request.getEmail())
                                .withPassword(request.getPassword())
                                .withPhone(request.getPhone())
                                .withAddress(request.getAddress())
                                .withImage(request.getImage())))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, storeId));
    }

    @Override
    public ResponseEntity<?> delete(Long storeId) {
        return storeRepository.findById(storeId).map(store -> {
                    storeRepository.delete(store);
                    return ResponseEntity.ok().build(); })
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, storeId));

    }

    @Override
    public Store addSalesOrderToStore(Long storeId, SalesOrder salesOrder) {
        return storeRepository.findById(storeId).map(store ->
                storeRepository.save(store.addSalesOrder(salesOrder)))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, storeId));
    }

    @Override
    public Store deleteSalesOrderToStore(Long storeId, Long salesOrderId) {
        return storeRepository.findById(storeId).map(store ->
                storeRepository.save(store.deleteSalesOrder(salesOrderId)))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, storeId));
    }

    @Override
    public Store updateSalesOrderToStore(Long storeId, Long salesOrderId, SalesOrder salesOrder) {
        return storeRepository.findById(storeId).map(store ->
                storeRepository.save(store.updateSalesOrder(salesOrder, salesOrderId)))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, storeId));
    }
}
