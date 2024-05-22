package com.Yaktta.Disco.models.mapper;

import com.Yaktta.Disco.models.entities.Product;
import com.Yaktta.Disco.models.request.ProductSaveRequest;
import com.Yaktta.Disco.models.response.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ProductMapper {
    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public ProductResponse mapProductEntityToDTO(Product product){
        return modelMapper.map(product, ProductResponse.class);
    }
    public Product mapProductSaveRequestToEntity(ProductSaveRequest productSaveRequest){
        return modelMapper.map(productSaveRequest, Product.class);
    }
    public byte[] stringToByteArray(String file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        // Elimina la parte de encabezado si está presente
        if (file.contains(",")) {
            file = file.split(",")[1];
        }
        // Decodifica la cadena base64 a una matriz de bytes
        return Base64.getDecoder().decode(file);
    }

}
