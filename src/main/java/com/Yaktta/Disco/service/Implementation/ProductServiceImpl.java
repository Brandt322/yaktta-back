package com.Yaktta.Disco.service.Implementation;

import com.Yaktta.Disco.exceptions.BadRequestException;
import com.Yaktta.Disco.exceptions.NotFoundException;
import com.Yaktta.Disco.models.entities.Product;
import com.Yaktta.Disco.models.mapper.ProductMapper;
import com.Yaktta.Disco.models.request.ProductSaveRequest;
import com.Yaktta.Disco.models.response.ProductResponse;
import com.Yaktta.Disco.repository.ProductRepository;
import com.Yaktta.Disco.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductResponse> findAll() {
        List<Product> products = this.productRepository.findAll();
        return products.stream()
                .map(product -> productMapper.mapProductEntityToDTO(product))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductResponse> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()){
            throw new NotFoundException("Product not found with id: "+id);
        }
        return Optional.of(productMapper.mapProductEntityToDTO(product.get()));
    }

    public ProductResponse save(ProductSaveRequest productSaveRequest){
        if (productSaveRequest == null) {
            throw new BadRequestException("Product cannot be null");
        }
        // Convierte el archivo a bytes utilizando el método stringToByteArray
        byte[] fileBytes = productMapper.stringToByteArray(productSaveRequest.getImage());
        Product product = productMapper.mapProductSaveRequestToEntity(productSaveRequest);
        product.setImage(fileBytes);
        Product saveProduct = productRepository.save(product);

        // Convierte los bytes de la imagen a una cadena base64 para incluir en la respuesta
        String imageBase64 = Base64.getEncoder().encodeToString(fileBytes);

        // Crea el ProductResponse con la cadena base64 de la imagen
        ProductResponse productResponse = productMapper.mapProductEntityToDTO(saveProduct);
        productResponse.setImage(imageBase64);

        return productResponse;
    }


    @Override
    public Product edit(Long id) {
        return null;
    }

    @Override
    public Product delete(Long id) {
        return null;
    }
}
