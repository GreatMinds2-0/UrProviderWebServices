package com.acme.urproviderwebservices.sales.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("salesMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public SalesOrderMapper salesOrderMapper() {
        return new SalesOrderMapper();
    }
    @Bean
    public ReviewMapper reviewMapper(){return new ReviewMapper();}
}
