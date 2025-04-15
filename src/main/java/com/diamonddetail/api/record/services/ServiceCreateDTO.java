package com.diamonddetail.api.record.services;

import com.diamonddetail.api.entities.ServiceEntity;

import java.math.BigDecimal;

public record ServiceCreateDTO(String name, BigDecimal price) {
    public ServiceEntity toEntity(){
        return new ServiceEntity(null, name, price, null);
    }
}
