package com.Yaktta.Disco.service.Implementation;

import com.Yaktta.Disco.exceptions.BadRequestException;
import com.Yaktta.Disco.exceptions.NotFoundException;
import com.Yaktta.Disco.models.entities.Brand;
import com.Yaktta.Disco.models.mapper.BrandMapper;
import com.Yaktta.Disco.models.request.BrandSaveRequest;
import com.Yaktta.Disco.models.response.BrandResponse;
import com.Yaktta.Disco.repository.BrandRepository;
import com.Yaktta.Disco.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public List<BrandResponse> findAll() {
        List<Brand> brands = this.brandRepository.findAll();
        return brands.stream()
                .map(brand -> brandMapper.mapBrandEntityToDTO(brand))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BrandResponse> findById(Long id) {
        Optional<Brand> brand = brandRepository.findById(id);
        if (!brand.isPresent()) {
            throw new NotFoundException("Brand not found with id: " + id);
        }
        return Optional.of(brandMapper.mapBrandEntityToDTO(brand.get()));
    }

    @Override
    public BrandResponse save(BrandSaveRequest brandSaveRequest) {

        if(brandSaveRequest ==  null) {
            throw new BadRequestException("Brand cannot be null");
        }

        Brand brand = brandMapper.mapBrandSaveRequestToEntity(brandSaveRequest);
        Brand saveBrand = brandRepository.save(brand);
        return brandMapper.mapBrandEntityToDTO(saveBrand);
    }

    @Override
    public void delete(Long id) {

    }

}

