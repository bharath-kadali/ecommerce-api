package com.example.ecommerce.model;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderItem {
    private String id;
    private String productId;
    private Integer quantity;
    private Double price;
}