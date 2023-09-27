package com.acme.urproviderwebservices.users.store.api;

import com.acme.urproviderwebservices.users.store.domain.service.StoreService;
import com.acme.urproviderwebservices.users.store.mapping.StoreMapper;
import com.acme.urproviderwebservices.users.store.resource.CreateStoreResource;
import com.acme.urproviderwebservices.users.store.resource.StoreResource;
import com.acme.urproviderwebservices.users.store.resource.UpdateStoreResource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/stores", produces = "application/json")
public class StoresController {

    private final StoreService storeService;
    private final StoreMapper mapper;
    public StoresController(StoreService storeService, StoreMapper mapper){
        this.storeService = storeService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<StoreResource> getAllStore(Pageable pageable){
        return mapper.modelListPage(storeService.getAll(), pageable);
    }
    @GetMapping("{storeId}")
    public StoreResource getStoreById(@PathVariable Long storeId){
        return mapper.toResource(storeService.getById(storeId));

    }
    @PostMapping
    public StoreResource createStore(@Valid @RequestBody CreateStoreResource resource){
        return mapper.toResource(storeService.create(mapper.toModel(resource)));
    }
    @PutMapping("{storeId}")
    public StoreResource updateStore(@PathVariable Long storeId,
                                     @RequestBody UpdateStoreResource resource){
        return mapper.toResource(storeService.update(storeId,mapper.toModel(resource)));
    }
    @DeleteMapping("{storeId}")
    public ResponseEntity<?> deleteStore(@PathVariable Long storeId){
        return storeService.delete(storeId);
    }
}
