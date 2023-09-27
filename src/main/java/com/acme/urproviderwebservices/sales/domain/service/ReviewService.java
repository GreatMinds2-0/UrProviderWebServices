package com.acme.urproviderwebservices.sales.domain.service;

import com.acme.urproviderwebservices.sales.domain.model.entity.Review;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {

    List<Review> getAll();
    Review getById(Long reviewId);
    Review getById(Long supplierID,Long reviewId);

    Review getByTitleAndSupplierId(Review title,Long id);
    Review update(Long reviewId, Review request);
    ResponseEntity<?> delete(Long reviewId);
}
