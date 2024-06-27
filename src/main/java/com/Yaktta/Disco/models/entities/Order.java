package com.Yaktta.Disco.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @Column(name = "order_date")
    private Date orderDate;

    private String description;
    private double amount;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}
