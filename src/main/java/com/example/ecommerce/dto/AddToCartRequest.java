package com.example.ecommerce.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class AddToCartRequest {
    private String userId;
    private String productId;
    private Integer quantity;
}