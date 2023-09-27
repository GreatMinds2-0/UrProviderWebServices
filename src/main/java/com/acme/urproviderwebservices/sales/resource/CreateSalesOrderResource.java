package com.acme.urproviderwebservices.sales.resource;

import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateSalesOrderResource {

    private String status;
    private String date;
    private String description;
}
