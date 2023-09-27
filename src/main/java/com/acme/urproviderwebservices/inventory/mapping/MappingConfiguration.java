package com.acme.urproviderwebservices.inventory.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("inventoryMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public ProductMapper productMapper() {
        return new ProductMapper();
    }
}
