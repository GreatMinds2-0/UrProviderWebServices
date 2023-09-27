package com.acme.urproviderwebservices.users.supplier.domain.model.entity;

import com.acme.urproviderwebservices.inventory.domain.model.entity.Product;
import com.acme.urproviderwebservices.sales.domain.model.entity.Review;
import com.acme.urproviderwebservices.shared.domain.model.BaseModel;
import com.acme.urproviderwebservices.shared.exception.ResourceValidationException;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;


@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 60)
    private String supplierName;
    @NotNull
    @NotBlank
    @Size(max = 60)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 60)
    private String lastName;

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String image;

    @NotNull
    @NotBlank
    @Column(unique = true)
    @Size(max = 60)
    private String email;

    @NotNull
    @NotBlank
    @Size(max = 60)
    private String address;


    private long ruc;

    @NotNull
    @NotBlank
    @Size(max = 60)
    private String category;

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String description;



    private long phone;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String password;


    private int likes = 1;

    // Relationship
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL,
    mappedBy = "supplier")
    private List<Product> products = new ArrayList<>();
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL,
    mappedBy = "supplier")
    private List<Review> reviews = new ArrayList<>();

    //Create Product by supplier
    public Supplier addProduct(Product product) {
        if (products == null) {
            products = new ArrayList<>();
        }


        if (!products.isEmpty()) {
            if(products.stream().anyMatch(products -> products.getName().equals(product.getName())))
                throw new ResourceValidationException("Product", "A product with the same name already exists");
        }
        products.add(new Product().withName(product.getName())
                        .withCategory(product.getCategory())
                        .withImage(product.getImage())
                        .withAvailable(product.isAvailable())
                        .withDescription(product.getDescription())
                .withSupplier(this));

        return this;
    }
    //Delete Product by supplier
    public Supplier deleteProduct(long productId){
        Product product= products.stream().filter(p -> p.getId()==productId).findAny().orElse(null);
        products.remove(product);
        return this;
    }

    //Update Product by supplier
    public  Supplier updateProduct(Product product,Long productId ){
        if (!products.isEmpty()) {
            if(products.stream().anyMatch(products -> (products.getName().equals(product.getName()))&&(!products.getId().equals(productId))))
                throw new ResourceValidationException("Product", "A product with the same name already exists");
        }

        Product item= products.stream().filter(p -> p.getId().equals(productId)).findAny().orElse(null);
        int index= products.indexOf(item);
       products.set(index,item
               .withName(product.getName())
               .withCategory(product.getCategory())
               .withImage(product.getImage())
               .withAvailable(product.isAvailable())
               .withDescription(product.getDescription()));

        return this;
    }
    //Create Review by supplier

    public Supplier addReview(Review review) {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }


        if (!reviews.isEmpty()) {
            if(reviews.stream().anyMatch(reviews -> reviews.getTitle().equals(review.getTitle())))
                throw new ResourceValidationException("Review", "A review with the same title already exists");
        }
        reviews.add(new Review().withTitle(review.getTitle())
                .withDescription(review.getDescription())
                        .withScore(review.getScore())
                .withSupplier(this));

        return this;
    }
    //Delete Review by supplier
    public Supplier deleteReview(long reviewId){
        Review review= reviews.stream().filter(p -> p.getId()==reviewId).findAny().orElse(null);
        reviews.remove(review);
        return this;
    }

    //Update Review by supplier
    public  Supplier updateReview(Review review,Long reviewId ){
        if (!reviews.isEmpty()) {
            if(reviews.stream().anyMatch(reviews -> (reviews.getTitle().equals(review.getTitle()))&&(!reviews.getId().equals(reviewId))))
                throw new ResourceValidationException("Review", "A review with the same title already exists");
        }

        Review item= reviews.stream().filter(p -> p.getId().equals(reviewId)).findAny().orElse(null);
        int index= reviews.indexOf(item);
        reviews.set(index,item
                .withTitle(review.getTitle())
                .withDescription(review.getDescription())
                .withScore(review.getScore()));

        return this;
    }
}
