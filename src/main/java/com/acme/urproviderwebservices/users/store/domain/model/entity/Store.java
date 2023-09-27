package com.acme.urproviderwebservices.users.store.domain.model.entity;

import com.acme.urproviderwebservices.sales.domain.model.entity.SalesOrder;
import com.acme.urproviderwebservices.shared.domain.model.BaseModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stores")
public class Store extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 60)
    private String storeName;

    @NotNull
    @NotBlank
    @Size(max = 60)
    private String name;


    @Size(max = 60)
    private String lastName;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String email;


    @Size(max = 60)
    private String address;

    private long phone;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String password;


    @NotBlank
    @Size(max = 200)
    private String image;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "store")
    private List<SalesOrder> salesOrders = new ArrayList<>();

    public Store addSalesOrder(SalesOrder salesOrder) {
        if (salesOrders == null) {
            salesOrders = new ArrayList<>();
        }

        salesOrders.add(new SalesOrder().withStatus(salesOrder.getStatus())
                .withDate(salesOrder.getDate())
                .withDescription(salesOrder.getDescription())
                .withStore(this));

        return this;
    }

    public Store deleteSalesOrder(long salesOrderId){
        SalesOrder salesOrder= salesOrders.stream().filter(s -> s.getId()==salesOrderId).findAny().orElse(null);
        salesOrders.remove(salesOrder);
        return this;
    }

    public Store updateSalesOrder(SalesOrder salesOrder,Long salesOrderId ){

        SalesOrder item= salesOrders.stream().filter(s -> s.getId().equals(salesOrderId)).findAny().orElse(null);
        int index= salesOrders.indexOf(item);
        salesOrders.set(index,item
                .withStatus(salesOrder.getStatus())
                .withDate(salesOrder.getDate())
                .withDescription(salesOrder.getDescription()));

        return this;
    }
}
