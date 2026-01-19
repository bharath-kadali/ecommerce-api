package com.example.ecommerce.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class CreateOrderRequest {
    private String userId;
}