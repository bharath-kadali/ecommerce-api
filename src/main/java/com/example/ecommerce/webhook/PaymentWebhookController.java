package com.example.ecommerce.webhook;
import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments/webhook")
public class PaymentWebhookController {
    @Autowired private PaymentService paymentService;
    
    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody PaymentWebhookRequest request) {
        paymentService.handleWebhook(request);
        return ResponseEntity.ok("Webhook processed");
    }
}