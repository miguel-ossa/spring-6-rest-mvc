package guru_springframework.spring_6_rest_mvc.events;

import guru_springframework.spring_6_rest_mvc.entities.Beer;
import org.springframework.security.core.Authentication;

public interface BeerEvent {

    Beer getBeer();

    Authentication getAuthentication();
}
