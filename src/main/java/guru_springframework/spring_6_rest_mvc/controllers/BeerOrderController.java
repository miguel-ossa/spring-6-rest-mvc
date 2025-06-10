package guru_springframework.spring_6_rest_mvc.controllers;

import guru_springframework.spring_6_rest_mvc.model.BeerOrderDTO;
import guru_springframework.spring_6_rest_mvc.services.BeerOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerOrderController {
    public static final String BEER_ORDER_PATH = "/api/v1/beerOrder";
    public static final String BEER_ORDER_PATH_ID = BEER_ORDER_PATH + "/{beerOrderId}";
    public static final String BEER_ORDER_ID = "beerOrderId";

    private final BeerOrderService beerOrderService;

    @GetMapping(value = BEER_ORDER_PATH)
    public Page<BeerOrderDTO> listBeerOrders(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return beerOrderService.listBeerOrders(pageNumber, pageSize);
    }

    @GetMapping(value = BEER_ORDER_PATH_ID)
    public BeerOrderDTO getBeerOrderById(@PathVariable(BEER_ORDER_ID) UUID beerOrderId) {
        return beerOrderService.getBeerOrderById(beerOrderId).orElseThrow(NotFoundException::new);
    }

//    @PostMapping(BEER_ORDER_PATH)
//    public ResponseEntity<HttpStatus> handlePost(@Validated @RequestBody BeerOrderDTO beerOrder) {
//        BeerOrderDTO savedBeerOrder = beerOrderService.saveNewBeerOrder(beerOrder);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", BEER_ORDER_PATH + "/" + savedBeerOrder.getId().toString());
//
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }
}
