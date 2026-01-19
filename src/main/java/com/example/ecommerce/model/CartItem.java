package com.example.ecommerce.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Document(collection = "cart_items")
public class CartItem {
    @Id private String id;
    private String userId;
    private String productId;
    private Integer quantity;
}