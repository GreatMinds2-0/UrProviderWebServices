package com.acme.urproviderwebservices.users.supplier.api;


import com.acme.urproviderwebservices.sales.domain.service.ReviewService;
import com.acme.urproviderwebservices.sales.mapping.ReviewMapper;
import com.acme.urproviderwebservices.sales.resource.CreateReviewResource;
import com.acme.urproviderwebservices.sales.resource.ReviewResource;
import com.acme.urproviderwebservices.users.supplier.domain.service.SupplierService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/suppliers/{supplierId}/reviews", produces = "application/json")
@Tag(name = "reviews-controller", description = "Create, read, update and delete reviews by supplier")
public class SupplierReviewsController {

    private final SupplierService supplierService;

    private final ReviewService reviewService;

    private final ReviewMapper mapper;

    public SupplierReviewsController(SupplierService supplierService, ReviewService reviewService, ReviewMapper mapper) {
        this.supplierService = supplierService;
        this.reviewService = reviewService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ReviewResource> getAllReviewsBySupplierId(@PathVariable Long supplierId) {
        return mapper.modelListPage(supplierService.getById(supplierId)
                .getReviews().stream().toList());
    }
    @GetMapping("{reviewId}")
    public ReviewResource getReviewById(@PathVariable Long reviewId, @PathVariable Long supplierId) {
        return mapper.toResource(reviewService.getById(supplierId,reviewId));
    }

    @PostMapping
    public ResponseEntity<ReviewResource> createProduct(@PathVariable Long supplierId,
                                                         @Valid @RequestBody CreateReviewResource resource) {
        // review name Uniqueness validation
        supplierService.addReviewToSupplier(supplierId,mapper.toModel(resource));
        //publish review
        return new ResponseEntity<>(mapper.toResource(reviewService.getByTitleAndSupplierId(mapper.toModel(resource),
                supplierId)), HttpStatus.CREATED);
    }

    @PutMapping("{reviewId}")
    public ReviewResource updateReview(@PathVariable Long reviewId,
                                         @RequestBody CreateReviewResource resource, @PathVariable Long supplierId) {
        supplierService.updateReviewToSupplier(supplierId,reviewId,mapper.toModel(resource));
        return mapper.toResource(reviewService.update(reviewId,mapper.toModel(resource)));
    }
    @DeleteMapping("{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long supplierId, @PathVariable Long reviewId){
        supplierService.deleteReviewToSupplier(supplierId,reviewId);
        return reviewService.delete(reviewId);
    }
}
