package guru_springframework.spring_6_rest_mvc.services;

import guru_springframework.spring_6_rest_mvc.model.BeerOrderDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerOrderService {
    List<BeerOrderDTO> getAllBeerOrders();

    Optional<BeerOrderDTO> getBeerOrderById(UUID id);
}
