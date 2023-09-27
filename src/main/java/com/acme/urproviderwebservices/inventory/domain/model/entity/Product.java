package com.acme.urproviderwebservices.inventory.domain.model.entity;

import com.acme.urproviderwebservices.inventory.resource.ProductResource;
import com.acme.urproviderwebservices.shared.domain.model.BaseModel;
import com.acme.urproviderwebservices.users.supplier.domain.model.entity.Supplier;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@With
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 60)
    private String name;
    private String category;
    private String image;
    private boolean available=true;
    private String description;
    private int numberOfSales=1;

    //Relationship

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    @JsonIgnore
    private Supplier supplier;

    public static Product from(ProductResource productResource){
        Product product=new Product();
        product.setCategory(productResource.getCategory());
        return product;

    }
}
