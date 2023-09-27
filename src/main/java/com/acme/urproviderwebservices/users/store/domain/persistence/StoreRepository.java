package com.acme.urproviderwebservices.users.store.domain.persistence;

import com.acme.urproviderwebservices.users.store.domain.model.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {
    Store findByEmail(String email);
}
