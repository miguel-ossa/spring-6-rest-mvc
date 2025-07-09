package guru_springframework.spring_6_rest_mvc.listeners;

import guru.springframework.spring6restmvcapi.events.OrderPlacedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedListener {

    @Async
    @EventListener
    public void listen(OrderPlacedEvent event) {
        //TODO: add send to Kafka
        System.out.println("Order Placed Event Received: " + event.getBeerOrderDTO());
    }
}
