package com.acme.urproviderwebservices.users.supplier.resource;

import lombok.*;


@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResource {
    private Long id;
    private String supplierName;
    private String name;
    private String lastName;
    private String image;
    private String email;
    private String address;
    private long ruc;
    private String category;
    private String description;
    private long phone;
    private String password;
    private int likes;
}
