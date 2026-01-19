package com.example.ecommerce.service;
import com.example.ecommerce.dto.CreateOrderRequest;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.*;

@Service
public class OrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired private CartService cartService;
    @Autowired private ProductRepository productRepository;
    
    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        List<CartItem> cartItems = cartService.getCartItems(request.getUserId());
        if (cartItems.isEmpty()) throw new RuntimeException("Cart is empty");
        
        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;
        
        for (CartItem ci : cartItems) {
            Optional<Product> po = productRepository.findById(ci.getProductId());
            if (po.isPresent()) {
                Product p = po.get();
                OrderItem oi = new OrderItem();
                oi.setId(UUID.randomUUID().toString());
                oi.setProductId(p.getId());
                oi.setQuantity(ci.getQuantity());
                oi.setPrice(p.getPrice());
                orderItems.add(oi);
                total += p.getPrice() * ci.getQuantity();
            }
        }
        
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setTotalAmount(total);
        order.setStatus("CREATED");
        order.setCreatedAt(Instant.now());
        order.setItems(orderItems);
        Order saved = orderRepository.save(order);
        cartService.clearCart(request.getUserId());
        return saved;
    }
    
    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }
    
    public void updateOrderStatus(String id, String status) {
        orderRepository.findById(id).ifPresent(o -> {
            o.setStatus(status);
            orderRepository.save(o);
        });
    }
}