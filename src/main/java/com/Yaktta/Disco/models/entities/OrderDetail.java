package com.Yaktta.Disco.models.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @Column(name = "id_order_detail")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrderDetail;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    private int quantity;

    @Column(name = "total_price")
    private double totalPrice;
}
