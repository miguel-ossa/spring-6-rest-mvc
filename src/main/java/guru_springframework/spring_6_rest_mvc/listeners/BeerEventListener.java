package guru_springframework.spring_6_rest_mvc.listeners;

import guru_springframework.spring_6_rest_mvc.events.*;
import guru_springframework.spring_6_rest_mvc.mappers.BeerMapper;
import guru_springframework.spring_6_rest_mvc.repositories.BeerAuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerEventListener {

    private final BeerMapper beerMapper;
    private final BeerAuditRepository beerAuditRepository;

    @Async
    @EventListener
    public void listen(BeerEvent event) {

        val beerAudit = beerMapper.beerToBeerAudit(event.getBeer());

        String eventType = null;

        switch (event) {
            case BeerCreatedEvent beerCreatedEvent -> eventType = "BEER_CREATED";
            case BeerUpdatedEvent beerCreatedEvent -> eventType = "BEER_UPDATED";
            case BeerPatchedEvent beerCreatedEvent -> eventType = "BEER_PATCHED";
            case BeerDeletedEvent beerCreatedEvent -> eventType = "BEER_DELETED";
            default -> eventType = "UNKNOWN";
        }

        beerAudit.setAuditEventType(eventType);

        if (event.getAuthentication() != null && event.getAuthentication().getName() != null) {
            beerAudit.setPrincipalName(event.getAuthentication().getName());
        }

        val savedBeerAudit = beerAuditRepository.save(beerAudit);
        log.debug("Beer Audit {" + eventType + "} Saved: " + savedBeerAudit.getId());
    }
}
