package guru_springframework.spring_6_rest_mvc.listeners;

import guru.springframework.spring6restmvcapi.events.OrderPlacedEvent;
import guru_springframework.spring_6_rest_mvc.config.kafkaConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class OrderPlacedKafkaListener {
    AtomicInteger messageCounter = new AtomicInteger(0);

    @KafkaListener(topics = kafkaConfig.ORDER_PLACED_TOPIC, groupId = "kafkaIntegrationTest")
    public void receive(OrderPlacedEvent orderPlacedEvent) {
        System.out.println("Received Message: " + orderPlacedEvent);
        messageCounter.incrementAndGet();
    }
}
