package com.acme.urproviderwebservices.sales.resource;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewResource {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String description;

    private int score;
}
