package com.Yaktta.Disco.service.Implementation;

import com.Yaktta.Disco.exceptions.BadRequestException;
import com.Yaktta.Disco.exceptions.NotFoundException;
import com.Yaktta.Disco.models.entities.Brand;
import com.Yaktta.Disco.models.mapper.BrandMapper;
import com.Yaktta.Disco.models.request.BrandSaveRequest;
import com.Yaktta.Disco.models.response.BrandResponse;
import com.Yaktta.Disco.repository.BrandRepository;
import com.Yaktta.Disco.service.BrandService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
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

        // Comprueba si ya existe un Brand con el mismo nombre
        Brand existingBrand = brandRepository.findByBrandName(brandSaveRequest.getBrandName());
        if (existingBrand != null) {
            throw new BadRequestException("Brand with name " + brandSaveRequest.getBrandName() + " already exists");
        }

        Brand brand = brandMapper.mapBrandSaveRequestToEntity(brandSaveRequest);
        Brand saveBrand = brandRepository.save(brand);
        return brandMapper.mapBrandEntityToDTO(saveBrand);
    }

    @Override
    public BrandResponse edit(Long id, BrandSaveRequest brandSaveRequest) {
        // Busca la marca existente por su id
        Optional<Brand> existingBrandOpt = brandRepository.findById(id);
        if (!existingBrandOpt.isPresent()) {
            throw new NotFoundException("Brand not found with id: " + id);
        }

        // Actualiza las propiedades de la marca existente con los valores del BrandSaveRequest
        Brand existingBrand = existingBrandOpt.get();
        existingBrand.setBrandName(brandSaveRequest.getBrandName());

        // Guarda la marca actualizada en la base de datos
        Brand updatedBrand = brandRepository.save(existingBrand);

        // Convierte la marca actualizada a BrandResponse y la devuelve
        return brandMapper.mapBrandEntityToDTO(updatedBrand);
    }

    @Override
    public String delete(Long id) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand not found with id: " + id));
        brandRepository.delete(existingBrand);
        return "Brand deleted successfully";
    }
}

