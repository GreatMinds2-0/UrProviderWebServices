package com.acme.urproviderwebservices.sales.domain.model.entity;

import com.acme.urproviderwebservices.shared.domain.model.BaseModel;
import com.acme.urproviderwebservices.users.store.domain.model.entity.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "salesOrders")
public class SalesOrder extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String date;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    @JsonIgnore
    private Store store;
}
