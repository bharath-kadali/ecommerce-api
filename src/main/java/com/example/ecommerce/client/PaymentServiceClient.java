package com.example.ecommerce.client;
import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.*;

@Component
public class PaymentServiceClient {
    @Autowired private RestTemplate restTemplate;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    public void triggerWebhookAsync(Payment payment) {
        scheduler.schedule(() -> {
            PaymentWebhookRequest req = new PaymentWebhookRequest();
            req.setPaymentId(payment.getPaymentId());
            req.setOrderId(payment.getOrderId());
            req.setStatus("SUCCESS");
            try {
                restTemplate.postForEntity(
                    "http://localhost:8080/api/payments/webhook",
                    req, String.class
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 3, TimeUnit.SECONDS);
    }
}