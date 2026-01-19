package com.example.ecommerce.service;
import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class CartService {
    @Autowired private CartRepository cartRepository;
    @Autowired private ProductRepository productRepository;
    
    public CartItem addToCart(AddToCartRequest request) {
        CartItem item = new CartItem();
        item.setUserId(request.getUserId());
        item.setProductId(request.getProductId());
        item.setQuantity(request.getQuantity());
        return cartRepository.save(item);
    }
    
    public List<CartItem> getCartItems(String userId) {
        return cartRepository.findByUserId(userId);
    }
    
    public Map<String, Object> getCartWithProducts(String userId) {
        List<CartItem> items = cartRepository.findByUserId(userId);
        List<Map<String, Object>> enriched = items.stream().map(item -> {
            Map<String, Object> e = new HashMap<>();
            e.put("id", item.getId());
            e.put("productId", item.getProductId());
            e.put("quantity", item.getQuantity());
            productRepository.findById(item.getProductId()).ifPresent(p -> {
                Map<String, Object> pi = new HashMap<>();
                pi.put("id", p.getId());
                pi.put("name", p.getName());
                pi.put("price", p.getPrice());
                e.put("product", pi);
            });
            return e;
        }).toList();
        Map<String, Object> result = new HashMap<>();
        result.put("items", enriched);
        return result;
    }
    
    @Transactional
    public void clearCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }
}