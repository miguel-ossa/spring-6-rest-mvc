package guru_springframework.spring_6_rest_mvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvcapi.model.*;
import guru_springframework.spring_6_rest_mvc.entities.BeerOrder;
import guru_springframework.spring_6_rest_mvc.repositories.BeerOrderRepository;
import guru_springframework.spring_6_rest_mvc.repositories.BeerRepository;
import guru_springframework.spring_6_rest_mvc.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static guru_springframework.spring_6_rest_mvc.controllers.BeerOrderControllerTest.jwtRequestPostProcessor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RecordApplicationEvents
@SpringBootTest
class BeerOrderControllerIT {

    @Autowired
    BeerOrderController beerOrderController;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Transactional
    @Test
    void testListBeerOrders() {
        Page<BeerOrderDTO> dtos = beerOrderController.listBeerOrders(1, 25);

        assertThat(dtos.getContent().size()).isGreaterThan(0);
    }

    @Transactional
    @Test
    void testGetBeerOrderById() {
        BeerOrder beerOrder = beerOrderRepository.findAll().getFirst();

        BeerOrderDTO dto = beerOrderController.getBeerOrderById(beerOrder.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void testCreateNewBeerOrder() throws Exception {
        val beer = beerRepository.findAll().getFirst();
        val customer = customerRepository.findAll().getFirst();

        val beerOrderCreateDTO = BeerOrderCreateDTO.builder()
                .customerId(customer.getId())
                .beerOrderLines(Set.of(BeerOrderLineCreateDTO.builder()
                        .beerId(beer.getId())
                        .orderQuantity(1)
                        .build()))
                .build();

        mockMvc.perform(post(BeerOrderController.BEER_ORDER_PATH)
                        .with(jwtRequestPostProcessor)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerOrderCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    @Transactional
    void testUpdateBeerOrder() throws Exception {
        // retrieve one order from repository
        val beerOrder = beerOrderRepository.findAll().getFirst();
        Set<BeerOrderLineUpdateDTO> lines = new HashSet<>();
        beerOrder.getBeerOrderLines().forEach(beerOrderLine -> {
            lines.add(BeerOrderLineUpdateDTO.builder()
                    .id(beerOrderLine.getId())
                    .beerId(beerOrderLine.getBeer().getId())
                    .orderQuantity(beerOrderLine.getOrderQuantity())
                    .quantityAllocated(beerOrderLine.getQuantityAllocated())
                    .build());
        });
        // update data
        final String customerRef = "Updated customerref";
        val beerOrderUpdateDTO = BeerOrderUpdateDTO.builder()
                .customerId(beerOrder.getCustomer().getId())
                .customerRef(customerRef) // update
                .beerOrderLines(lines) // no changes in lines
                .beerOrderShipment(BeerOrderShipmentUpdateDTO.builder()
                        .trackingNumber("123333333") // update
                        .build())
                .build();

        // call updateById in controller and check status
        mockMvc.perform(put(BeerOrderController.BEER_ORDER_PATH_ID, beerOrder.getId())
                        .with(jwtRequestPostProcessor)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerOrderUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerRef", is(customerRef)));
    }

    @Test
    void testDeleteBeerOrder() throws Exception {
        // retrieve one order from repository
        val beerOrder = beerOrderRepository.findAll().getFirst();

        // call deleteById in controller and check status
        mockMvc.perform(delete(BeerOrderController.BEER_ORDER_PATH_ID, beerOrder.getId())
                        .with(jwtRequestPostProcessor))
                .andExpect(status().isNoContent());

        assertTrue(beerOrderRepository.findById(beerOrder.getId()).isEmpty());

        mockMvc.perform(delete(BeerOrderController.BEER_ORDER_PATH_ID, beerOrder.getId())
                        .with(jwtRequestPostProcessor))
                .andExpect(status().isNotFound());
    }
}