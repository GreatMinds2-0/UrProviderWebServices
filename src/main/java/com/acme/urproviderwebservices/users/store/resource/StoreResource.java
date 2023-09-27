package com.acme.urproviderwebservices.users.store.resource;
import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class StoreResource {
    private Long id;
    private String storeName;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Long phone;
    private String address;
    private String image;
}
