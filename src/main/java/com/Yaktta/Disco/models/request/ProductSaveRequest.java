package com.Yaktta.Disco.models.request;

import com.Yaktta.Disco.models.entities.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductSaveRequest {
    private String name;
    private double height;
    private double width;
    private String color;
    private String description;
    private int quantity;
    private double price;
    private double discount;
    private String product_type;
    private String image;
    private Brand id_brands;
}
