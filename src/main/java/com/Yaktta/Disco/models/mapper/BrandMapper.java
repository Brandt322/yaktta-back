package com.Yaktta.Disco.models.mapper;

import com.Yaktta.Disco.models.entities.Brand;
import com.Yaktta.Disco.models.request.BrandSaveRequest;
import com.Yaktta.Disco.models.response.BrandResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {
    private final ModelMapper modelMapper;

    public BrandMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BrandResponse mapBrandEntityToDTO(Brand brand) {
        return modelMapper.map(brand, BrandResponse.class);
    }

    public Brand mapBrandSaveRequestToEntity(BrandSaveRequest brandSaveRequest) {
        Brand brand = modelMapper.map(brandSaveRequest, Brand.class);
        brand.setId(null);
        return brand;
    }
}
