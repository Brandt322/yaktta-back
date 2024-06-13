package com.Yaktta.Disco.service.Implementation;

import com.Yaktta.Disco.exceptions.BadRequestException;
import com.Yaktta.Disco.exceptions.NotFoundException;
import com.Yaktta.Disco.models.entities.Brand;
import com.Yaktta.Disco.models.entities.Product;
import com.Yaktta.Disco.models.mapper.ProductMapper;
import com.Yaktta.Disco.models.request.ProductSaveRequest;
import com.Yaktta.Disco.models.response.ProductResponse;
import com.Yaktta.Disco.repository.BrandRepository;
import com.Yaktta.Disco.repository.ProductRepository;
import com.Yaktta.Disco.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Transactional
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, BrandRepository brandRepository){
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
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

    @Override
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

        // Busca la marca por su ID y establece el nombre de la marca en el ProductResponse
        Brand brand = brandRepository.findById(productSaveRequest.getId_brands().getId())
                .orElseThrow(() -> new NotFoundException("Brand not found with id: " + productSaveRequest.getId_brands().getId()));
        productResponse.getId_brands().setBrandName(brand.getBrandName());

        return productResponse;
    }

    @Override
    public ProductResponse edit(Long id, ProductSaveRequest productSaveRequest) {
        // Busca el producto existente
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        // Actualiza los campos del producto existente con los valores del ProductSaveRequest
        existingProduct.setName(productSaveRequest.getName());
        existingProduct.setDescription(productSaveRequest.getDescription());
        existingProduct.setPrice(productSaveRequest.getPrice());
        existingProduct.setStock(productSaveRequest.getStock());
        existingProduct.setDiscount(productSaveRequest.getDiscount());
        existingProduct.setProduct_type(productSaveRequest.getProduct_type());
        existingProduct.setImage(productMapper.stringToByteArray(productSaveRequest.getImage()));
        existingProduct.setId_brands(productSaveRequest.getId_brands());

        // Guarda el producto actualizado en la base de datos
        Product updatedProduct = productRepository.save(existingProduct);

        // Convierte el producto actualizado a un ProductResponse y lo devuelve
        ProductResponse productResponse = productMapper.mapProductEntityToDTO(updatedProduct);
        // Convierte los bytes de la imagen a una cadena base64
        String imageBase64 = Base64.getEncoder().encodeToString(updatedProduct.getImage());
        // Establece la imagen en el ProductResponse
        productResponse.setImage(imageBase64);

        return productResponse;
    }

    @Override
    public String delete(Long id) {
        // Busca el producto existente
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        // Elimina el producto
        productRepository.delete(existingProduct);
        // Devuelve un mensaje de confirmación
        return "Product with id: " + id + " was deleted successfully";
    }
}
