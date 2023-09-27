package com.acme.urproviderwebservices.sales.resource;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResource {
    private Long id;
    private String title;

    private String description;
    private int score;
}
