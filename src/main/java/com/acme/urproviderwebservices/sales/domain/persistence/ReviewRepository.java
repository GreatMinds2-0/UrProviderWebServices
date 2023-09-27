package com.acme.urproviderwebservices.sales.domain.persistence;

import com.acme.urproviderwebservices.sales.domain.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    List<Review> findBySupplierId(Long storeId);

    Review findReviewById(Long reviewId);

    Optional<Review> findByTitleAndSupplierId(String title, Long supplierId);

}
