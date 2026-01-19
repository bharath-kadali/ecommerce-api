package com.example.ecommerce.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class PaymentWebhookRequest {
    private String paymentId;
    private String orderId;
    private String status;
}