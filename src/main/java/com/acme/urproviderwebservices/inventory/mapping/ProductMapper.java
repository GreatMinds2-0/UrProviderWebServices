package com.acme.urproviderwebservices.inventory.mapping;

import com.acme.urproviderwebservices.inventory.domain.model.entity.Product;
import com.acme.urproviderwebservices.inventory.resource.CreateProductResource;
import com.acme.urproviderwebservices.inventory.resource.ProductResource;
import com.acme.urproviderwebservices.inventory.resource.UpdateProductResource;
import com.acme.urproviderwebservices.shared.mapping.EnhancedModelMapper;
import com.acme.urproviderwebservices.users.supplier.domain.model.entity.Supplier;
import com.acme.urproviderwebservices.users.supplier.resource.CreateSupplierResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class ProductMapper implements Serializable {

    @Autowired
    EnhancedModelMapper mapper;

    public ProductResource toResource(Product model) { return mapper.map(model, ProductResource.class);    }

    public List<ProductResource> modelListPage(List<Product> modelList) {
        return mapper.mapList(modelList, ProductResource.class);
    }
    public Product toModel(CreateProductResource resource) {
        return mapper.map(resource, Product.class);
    }

    public Product toModel(CreateProductResource resource) {
        return mapper.map(resource, Product.class);
    }

    public Product toModel(UpdateProductResource resource) {
        return mapper.map(resource, Product.class);
    }
}
