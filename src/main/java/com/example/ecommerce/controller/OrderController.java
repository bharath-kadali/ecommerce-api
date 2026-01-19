package com.example.ecommerce.controller;
import com.example.ecommerce.dto.CreateOrderRequest;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.repository.PaymentRepository;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired private OrderService orderService;
    @Autowired private PaymentRepository paymentRepository;
    
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrder(@PathVariable String orderId) {
        return orderService.getOrderById(orderId).map(o -> {
            Map<String, Object> r = new HashMap<>();
            r.put("id", o.getId());
            r.put("userId", o.getUserId());
            r.put("totalAmount", o.getTotalAmount());
            r.put("status", o.getStatus());
            r.put("items", o.getItems());
            paymentRepository.findByOrderId(orderId).ifPresent(p -> {
                r.put("payment", Map.of("id", p.getId(), "status", p.getStatus(), "amount", p.getAmount()));
            });
            return ResponseEntity.ok(r);
        }).orElse(ResponseEntity.notFound().build());
    }
}