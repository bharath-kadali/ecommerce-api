package com.example.ecommerce.service;
import com.example.ecommerce.dto.*;
import com.example.ecommerce.model.Payment;
import com.example.ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private OrderService orderService;
    
    public Payment createPayment(PaymentRequest request) {
        Payment p = new Payment();
        p.setOrderId(request.getOrderId());
        p.setAmount(request.getAmount());
        p.setStatus("PENDING");
        p.setPaymentId("pay_mock_" + UUID.randomUUID());
        p.setCreatedAt(Instant.now());
        return paymentRepository.save(p);
    }
    
    public void handleWebhook(PaymentWebhookRequest req) {
        paymentRepository.findByPaymentId(req.getPaymentId()).ifPresent(p -> {
            p.setStatus(req.getStatus());
            paymentRepository.save(p);
            String status = req.getStatus().equals("SUCCESS") ? "PAID" : "FAILED";
            orderService.updateOrderStatus(p.getOrderId(), status);
        });
    }
}