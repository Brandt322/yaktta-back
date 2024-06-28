package com.Yaktta.Disco.stripe.service;

import com.Yaktta.Disco.exceptions.BadRequestException;
import com.Yaktta.Disco.exceptions.NotFoundException;
import com.Yaktta.Disco.models.entities.Order;
import com.Yaktta.Disco.models.entities.OrderDetail;
import com.Yaktta.Disco.models.entities.Product;
import com.Yaktta.Disco.models.entities.User;
import com.Yaktta.Disco.repository.OrderDetailRepository;
import com.Yaktta.Disco.repository.OrderRepository;
import com.Yaktta.Disco.repository.ProductRepository;
import com.Yaktta.Disco.service.Implementation.UserServiceImpl;
import com.Yaktta.Disco.stripe.models.PaymentIntentDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class PaymentService {
    @Value("${stripe.key.secret}")
    String secretKey;

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserServiceImpl userService;

    public PaymentService(ProductRepository productRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, UserServiceImpl userService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userService = userService;
    }

    public PaymentIntent paymentIntent(PaymentIntentDto paymentIntentDto) throws StripeException {
        Stripe.apiKey = secretKey;

        for (PaymentIntentDto.Product productDto : paymentIntentDto.getProducts()) {
            Product product = productRepository.findById(productDto.getId())
                    .orElseThrow(() -> new NotFoundException("Producto no encontrado con nombre: " + productDto.getName()));
            if (product.getStock() < productDto.getQuantity()) {
                throw new BadRequestException("No hay suficiiente stock para el product: " + productDto.getName());
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("amount", Math.round(paymentIntentDto.getAmount() * 100));
        params.put("description", paymentIntentDto.getDescription());
        params.put("currency", paymentIntentDto.getCurrency().toString());

        ArrayList<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        params.put("payment_method_types", paymentMethodTypes);
        return PaymentIntent.create(params);
    }

    public PaymentIntent confirm(String id, PaymentIntentDto paymentIntentDto) throws StripeException {
        Stripe.apiKey = secretKey;
        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        Map<String, Object> params = new HashMap<>();
        params.put("payment_method", "pm_card_visa");
        paymentIntent.confirm(params);

        User client = userService.getUserById(paymentIntentDto.getClientId());

        // Crear la orden
        Order order = new Order();
        order.setClientId(client);
        order.setOrderDate(new Date());
        order.setDescription(paymentIntentDto.getDescription());
        order.setAmount(paymentIntentDto.getAmount());
        orderRepository.save(order);

        // Crear los detalles de la orden
        for (PaymentIntentDto.Product productDto : paymentIntentDto.getProducts()) {
            Product product = productRepository.findById(productDto.getId()).orElseThrow();
            if (product.getStock() < productDto.getQuantity()) {
                throw new BadRequestException("No hay suficiente stock para el producto: " + productDto.getName());
            }
            product.setStock(product.getStock() - productDto.getQuantity());
            productRepository.save(product);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(productRepository.findById(productDto.getId()).orElseThrow());
            orderDetail.setQuantity(productDto.getQuantity());
            orderDetail.setTotalPrice(productDto.getPrice() * productDto.getQuantity());
            orderDetailRepository.save(orderDetail);
        }
        return paymentIntent;
    }

    public PaymentIntent cancel(String id) throws StripeException {
        Stripe.apiKey = secretKey;
        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        paymentIntent.cancel();
        return paymentIntent;
    }
}
