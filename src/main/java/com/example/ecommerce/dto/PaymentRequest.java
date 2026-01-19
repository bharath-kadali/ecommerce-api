package com.example.ecommerce.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class PaymentRequest {
    private String orderId;
    private Double amount;
}