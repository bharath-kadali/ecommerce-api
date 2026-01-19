package com.example.ecommerce.controller;
import com.example.ecommerce.dto.PaymentRequest;
import com.example.ecommerce.model.Payment;
import com.example.ecommerce.service.PaymentService;
import com.example.ecommerce.client.PaymentServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired private PaymentService paymentService;
    @Autowired private PaymentServiceClient paymentServiceClient;
    
    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequest request) {
        Payment payment = paymentService.createPayment(request);
        paymentServiceClient.triggerWebhookAsync(payment);
        return ResponseEntity.ok(payment);
    }
}