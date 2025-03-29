package guru_springframework.spring_6_rest_mvc.controllers;

import guru_springframework.spring_6_rest_mvc.model.Beer;
import guru_springframework.spring_6_rest_mvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

    public Beer getBeerById(UUID id) {

        log.debug("Get Beer by Id - in controller. Id: " + id.toString());

        return beerService.getBeerById(id);
    }
}
