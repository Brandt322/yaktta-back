package com.Yaktta.Disco.service.Implementation;

import com.Yaktta.Disco.exceptions.NotFoundException;
import com.Yaktta.Disco.models.entities.Order;
import com.Yaktta.Disco.models.entities.User;
import com.Yaktta.Disco.models.mapper.OrderMapper;
import com.Yaktta.Disco.models.response.OrderResponse;
import com.Yaktta.Disco.repository.OrderRepository;
import com.Yaktta.Disco.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserServiceImpl userService;

    public OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository, UserServiceImpl userService) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Override
    public List<OrderResponse> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> orderMapper.mapOrderEntityToDTO(order))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderResponse> findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()) {
            throw new NotFoundException("Order not found with id: " + id);
        }
        return Optional.of(orderMapper.mapOrderEntityToDTO(order.get()));
    }

    @Override
    public List<OrderResponse> findByClientId(Long clientId) {
        User client = userService.getUserById(clientId);
        List<Order> orders = orderRepository.findByClientId(client);
        if (orders.isEmpty()) {
            throw new NotFoundException("Orders not found for client id: " + clientId);
        }

        return orders.stream()
                .map(orderMapper::mapOrderEntityToDTO)
                .collect(Collectors.toList());
    }
}
