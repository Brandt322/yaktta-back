package com.Yaktta.Disco.controller;

import com.Yaktta.Disco.exceptions.NotFoundException;
import com.Yaktta.Disco.models.response.OrderResponse;
import com.Yaktta.Disco.service.Implementation.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            Optional<OrderResponse> order = orderService.findById(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Object> findByClientId(@PathVariable Long clientId) {
        try {
            List<OrderResponse> order = orderService.findByClientId(clientId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/order/{id}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestBody boolean status) {
        orderService.updateOrderStatus(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
