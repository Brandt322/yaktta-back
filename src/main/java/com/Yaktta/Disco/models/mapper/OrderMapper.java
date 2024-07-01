package com.Yaktta.Disco.models.mapper;

import com.Yaktta.Disco.models.entities.Order;
import com.Yaktta.Disco.models.response.OrderResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderResponse mapOrderEntityToDTO(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }
}
