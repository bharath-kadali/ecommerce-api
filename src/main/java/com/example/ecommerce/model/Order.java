package com.example.ecommerce.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import java.time.Instant;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Document(collection = "orders")
public class    Order {
    @Id private String id;
    private String userId;
    private Double totalAmount;
    private String status;
    private Instant createdAt;
    private List<OrderItem> items;
}