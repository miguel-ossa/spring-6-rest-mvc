package guru_springframework.spring_6_rest_mvc.bootstrap;

import guru.springframework.spring6restmvcapi.model.BeerStyle;
import guru_springframework.spring_6_rest_mvc.entities.*;
import guru_springframework.spring_6_rest_mvc.model.BeerCSVRecord;
import guru_springframework.spring_6_rest_mvc.repositories.BeerOrderRepository;
import guru_springframework.spring_6_rest_mvc.repositories.BeerRepository;
import guru_springframework.spring_6_rest_mvc.repositories.CustomerRepository;
import guru_springframework.spring_6_rest_mvc.services.BeerCsvService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerCsvService beerCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCSVData();
        loadCustomerData();
        //beerOrderRepository.deleteAll();
        if (beerOrderRepository.count() == 0) {
            loadOrdersData();
            loadOrderDataJohn();
        }
    }

    private void loadCSVData() throws FileNotFoundException {
        if (beerRepository.count() < 10) {
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

            List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);

            recs.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                        .beerStyle(beerStyle)
                        .price(BigDecimal.TEN)
                        .upc(beerCSVRecord.getRow().toString())
                        .quantityOnHand(beerCSVRecord.getCount())
                        .build());
            });
        }
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12356")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle(BeerStyle.IPA)
                    .upc("12356")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .customerName("Miguel")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("Antonio")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2));
        }
    }

    private void loadOrdersData() {
        List<Beer> beers = beerRepository.findAll();
        List<Customer> customers = customerRepository.findAll();

        BeerOrder order1 = new BeerOrder();
        order1.setCustomer(customers.getFirst());
        order1.setCustomerRef("First customer");

        BeerOrderLine orderLine1 = BeerOrderLine.builder()
                .beerOrder(order1)
                .beer(beers.getFirst())
                .build();

        BeerOrderLine orderLine2 = BeerOrderLine.builder()
                .beerOrder(order1)
                .beer(beers.get(1))
                .build();

        Set<BeerOrderLine> beerOrderLines = new HashSet<>(Arrays.asList(orderLine1, orderLine2));
        order1.setBeerOrderLines(beerOrderLines);

        BeerOrderShipment shipment1 = BeerOrderShipment.builder()
                .beerOrder(order1)
                .trackingNumber("1234566667")
                .build();
        order1.setBeerOrderShipment(shipment1);

        BeerOrder order2 = new BeerOrder();
        order2.setCustomer(customers.get(1));
        order2.setCustomerRef(customers.get(1).getCustomerName());

        BeerOrderLine orderLine3 = BeerOrderLine.builder()
                .beerOrder(order2)
                .beer(beers.get(2))
                .build();
        order2.addBeerOrderLine(orderLine3);

        beerOrderRepository.save(order1);
        beerOrderRepository.save(order2);
    }

    private void loadOrderDataJohn() {
        val customers = customerRepository.findAll();
        val beers = beerRepository.findAll();

        val beerIterator = beers.iterator();

        customers.forEach(customer -> {

            beerOrderRepository.save(BeerOrder.builder()
                    .customer(customer)
                    .beerOrderLines(Set.of(
                            BeerOrderLine.builder()
                                    .beer(beerIterator.next())
                                    .orderQuantity(1)
                                    .build(),
                            BeerOrderLine.builder()
                                    .beer(beerIterator.next())
                                    .orderQuantity(2)
                                    .build()
                    )).build());

            beerOrderRepository.save(BeerOrder.builder()
                    .customer(customer)
                    .beerOrderLines(Set.of(
                            BeerOrderLine.builder()
                                    .beer(beerIterator.next())
                                    .orderQuantity(1)
                                    .build(),
                            BeerOrderLine.builder()
                                    .beer(beerIterator.next())
                                    .orderQuantity(2)
                                    .build()
                    ))
                    .build());
        });

        //val orders = beerOrderRepository.findAll();
    }
}
