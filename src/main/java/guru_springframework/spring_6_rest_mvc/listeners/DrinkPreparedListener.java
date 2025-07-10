package guru_springframework.spring_6_rest_mvc.listeners;

import guru.springframework.spring6restmvcapi.events.DrinkPreparedEvent;
import guru.springframework.spring6restmvcapi.model.BeerOrderLineStatus;
import guru_springframework.spring_6_rest_mvc.config.KafkaConfig;
import guru_springframework.spring_6_rest_mvc.repositories.BeerOrderLineRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DrinkPreparedListener {

    private final BeerOrderLineRepository beerOrderLineRepository;

    @KafkaListener(topics = KafkaConfig.DRINK_PREPARED_TOPIC, groupId = "DrinkPreparedListener")
    public void listen(DrinkPreparedEvent event) {
        beerOrderLineRepository.findById(event.getBeerOrderLine().getId()).ifPresentOrElse(beerOrderLine -> {

            beerOrderLine.setOrderLineStatus(BeerOrderLineStatus.COMPLETE);

            beerOrderLineRepository.save(beerOrderLine);
        }, () -> log.error("Beer Order Line Not Found!"));
    }
}
