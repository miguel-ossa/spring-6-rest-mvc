package guru_springframework.spring_6_rest_mvc.events;

import guru_springframework.spring_6_rest_mvc.entities.Beer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BeerDeletedEvent implements BeerEvent {

    private Beer beer;

    private Authentication authentication;
}
