package com.Yaktta.Disco.models.response;

import com.Yaktta.Disco.models.entities.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long idOrder;
    private Long clientId;
    private Date orderDate;
    private String description;
    private double amount;
    private boolean status;
    private List<OrderDetail> orderDetails;
}
