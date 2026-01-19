# Ecommerce API

A Spring Boot REST API for managing an ecommerce platform with support for products, shopping carts, orders, and payment processing.

## Overview

This is a full-featured ecommerce backend application built with Spring Boot 3.2 and MongoDB. It provides comprehensive APIs for managing products, user shopping carts, orders, and payment transactions with webhook support for payment status updates.

## Tech Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: MongoDB
- **Build Tool**: Maven
- **Additional Libraries**: Lombok (for reducing boilerplate code)

## Project Structure

```
ecommerce/
├── src/
│   ├── main/
│   │   ├── java/com/example/ecommerce/
│   │   │   ├── EcommerceApplication.java       # Main entry point
│   │   │   ├── client/
│   │   │   │   └── PaymentServiceClient.java   # External payment service integration
│   │   │   ├── config/
│   │   │   │   └── RestTemplateConfig.java     # REST template configuration
│   │   │   ├── controller/
│   │   │   │   ├── CartController.java         # Shopping cart endpoints
│   │   │   │   ├── OrderController.java        # Order management endpoints
│   │   │   │   ├── PaymentController.java      # Payment processing endpoints
│   │   │   │   └── ProductController.java      # Product catalog endpoints
│   │   │   ├── dto/
│   │   │   │   ├── AddToCartRequest.java
│   │   │   │   ├── CreateOrderRequest.java
│   │   │   │   ├── PaymentRequest.java
│   │   │   │   └── PaymentWebhookRequest.java
│   │   │   ├── model/
│   │   │   │   ├── CartItem.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── OrderItem.java
│   │   │   │   ├── Payment.java
│   │   │   │   ├── Product.java
│   │   │   │   └── User.java
│   │   │   ├── repository/
│   │   │   │   ├── CartRepository.java
│   │   │   │   ├── OrderRepository.java
│   │   │   │   ├── PaymentRepository.java
│   │   │   │   └── ProductRepository.java
│   │   │   ├── service/
│   │   │   │   ├── CartService.java
│   │   │   │   ├── OrderService.java
│   │   │   │   ├── PaymentService.java
│   │   │   │   └── ProductService.java
│   │   │   └── webhook/
│   │   │       └── PaymentWebhookController.java # Payment notification handling
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/org/example/ecommerce/
│           └── EcommerceApplicationTests.java
└── pom.xml                                      # Maven dependencies
```

## Features

### Product Management
- Create and retrieve products
- Browse product catalog
- Product inventory management

### Shopping Cart
- Add items to cart
- View cart contents
- Manage cart items
- Calculate cart totals

### Order Management
- Create orders from cart items
- Track order status
- Retrieve order details with associated payment information
- Order history tracking

### Payment Processing
- Process payments for orders
- Integration with external payment service
- Payment webhook handling for real-time status updates
- Payment status tracking

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB 4.0+

## Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd ecommerce
```

### 2. Configure MongoDB
Update `src/main/resources/application.properties` with your MongoDB connection details:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/ecommerce
```

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## API Endpoints

### Products
- **POST** `/api/products` - Create a new product
- **GET** `/api/products` - Get all products

### Shopping Cart
- **POST** `/api/cart` - Add item to cart
- **GET** `/api/cart` - View cart contents
- **DELETE** `/api/cart/{itemId}` - Remove item from cart

### Orders
- **POST** `/api/orders` - Create a new order
- **GET** `/api/orders/{orderId}` - Get order details with payment information

### Payments
- **POST** `/api/payments` - Process a payment
- **POST** `/api/webhooks/payments` - Handle payment status updates from external service

## Configuration

### Application Properties
```properties
# Server Configuration
spring.application.name=ecommerce
server.port=8080

# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/ecommerce
spring.data.mongodb.auto-index-creation=true
```

## Database Schema

### Collections

**Product**
- id: String (ObjectId)
- name: String
- description: String
- price: Double
- quantity: Integer

**User**
- id: String (ObjectId)
- username: String
- email: String

**Cart**
- id: String (ObjectId)
- userId: String
- items: List<CartItem>
- createdDate: LocalDateTime

**CartItem**
- productId: String
- productName: String
- quantity: Integer
- price: Double

**Order**
- id: String (ObjectId)
- userId: String
- items: List<OrderItem>
- totalAmount: Double
- status: String (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- createdDate: LocalDateTime

**OrderItem**
- productId: String
- productName: String
- quantity: Integer
- price: Double

**Payment**
- id: String (ObjectId)
- orderId: String
- amount: Double
- status: String (PENDING, SUCCESS, FAILED, CANCELLED)
- paymentMethod: String
- transactionId: String
- createdDate: LocalDateTime

## Testing

Run the test suite:
```bash
mvn test
```

## External Integration

The API integrates with an external payment service via the `PaymentServiceClient`. Configure your payment service endpoints and credentials as needed.

### Payment Webhook
The application exposes a webhook endpoint at `/api/webhooks/payments` to receive real-time payment status updates from the payment provider.

## Error Handling

The API provides appropriate HTTP status codes:
- **200 OK** - Successful request
- **201 Created** - Resource created successfully
- **400 Bad Request** - Invalid request parameters
- **404 Not Found** - Resource not found
- **500 Internal Server Error** - Server error

## Troubleshooting

### MongoDB Connection Issues
- Ensure MongoDB is running on `localhost:27017`
- Check connection string in `application.properties`
- Verify database name is correct
