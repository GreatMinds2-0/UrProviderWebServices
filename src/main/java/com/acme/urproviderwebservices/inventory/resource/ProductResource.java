package com.acme.urproviderwebservices.inventory.resource;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class ProductResource {
    private Long id;
    private String name;
    private String category;
    private String image;
    private boolean available;
    private String description;
    private int numberOfSales;
    private Long supplierId;
}
