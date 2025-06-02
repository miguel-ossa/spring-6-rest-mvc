package guru_springframework.spring_6_rest_mvc.listeners;

import guru_springframework.spring_6_rest_mvc.events.BeerCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class BeerCreatedListener {

    @Async
    @EventListener
    public void listen(BeerCreatedEvent event) {
        System.out.println("I heard a beer was created!");
        System.out.println(event.getBeer().getId());

        System.out.println("Current Thread Name: " + Thread.currentThread().getName());
        System.out.println("Current Thread ID: " + Thread.currentThread().getId());

        //todo impl - add real implementation to persist audit record
    }
}
