package guru_springframework.spring_6_rest_mvc.services;

import guru_springframework.spring_6_rest_mvc.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerOrderServiceImpl implements BeerOrderService {

    private final Map<UUID, BeerOrderDTO> beerOrderDTOMap;

    public BeerOrderServiceImpl() {
        this.beerOrderDTOMap = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Miguel")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Pepe")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        BeerOrderDTO order1 =  BeerOrderDTO.builder()
                .id(UUID.randomUUID())
                .customer(customer1)
                .customerRef(customer1.getCustomerName())
                .build();

        BeerOrderLineDTO orderLine1 = BeerOrderLineDTO.builder()
                .beer(beer1)
                .orderQuantity(10)
                .build();

        BeerOrderLineDTO orderLine2 = BeerOrderLineDTO.builder()
                .beer(beer2)
                .orderQuantity(20)
                .build();

        Set<BeerOrderLineDTO> beerOrderLines = new HashSet<>(Arrays.asList(orderLine1, orderLine2));
        order1.setBeerOrderLines(beerOrderLines);

        BeerOrderShipmentDTO shipment1 = BeerOrderShipmentDTO.builder()
                .trackingNumber("1234566667")
                .build();
        order1.setBeerOrderShipment(shipment1);

        BeerOrderDTO order2 =  BeerOrderDTO.builder()
                .id(UUID.randomUUID())
                .customer(customer2)
                .customerRef(customer2.getCustomerName())
                .build();

        BeerOrderLineDTO orderLine3 = BeerOrderLineDTO.builder()
                .beer(beer1)
                .orderQuantity(10)
                .build();

        BeerOrderLineDTO orderLine4 = BeerOrderLineDTO.builder()
                .beer(beer2)
                .orderQuantity(1)
                .build();

        Set<BeerOrderLineDTO> beerOrderLines1 = new HashSet<>(Arrays.asList(orderLine3, orderLine4));
        order2.setBeerOrderLines(beerOrderLines1);

        beerOrderDTOMap.put(order1.getId(), order1);
        beerOrderDTOMap.put(order2.getId(), order2);
    }

    @Override
    public Page<BeerOrderDTO> listBeerOrders(Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(beerOrderDTOMap.values()));
    }

    @Override
    public Optional<BeerOrderDTO> getBeerOrderById(UUID id) {
        return Optional.of(beerOrderDTOMap.get(id));
    }
}
