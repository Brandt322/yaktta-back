package com.Yaktta.Disco.stripe.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentIntentDto {

    public enum Currency {
        USD, EUR, JPY, PEN;
    }
    @Getter
    @Setter
    public static class Product {
        private Long id;
        private double price;
        private int quantity;
        private String name;
    }

    private String token;
    private Long clientId;
    private List<Product> products;
    private String description;
    private double amount;
    private Currency currency;
}
