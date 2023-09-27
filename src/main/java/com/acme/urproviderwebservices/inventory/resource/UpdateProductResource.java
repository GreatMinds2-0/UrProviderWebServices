package com.acme.urproviderwebservices.inventory.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateProductResource {

    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String category;

    private String description;

    @NotNull
    @NotBlank
    private String image;

    private int numberOfSales;

    private boolean available;
}
