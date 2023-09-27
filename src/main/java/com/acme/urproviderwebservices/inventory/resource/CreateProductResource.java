package com.acme.urproviderwebservices.inventory.resource;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductResource {

    @NotNull
    @NotBlank
    private String name;
    private String category;
    private String image;
    private boolean available;
    private String description;


}
