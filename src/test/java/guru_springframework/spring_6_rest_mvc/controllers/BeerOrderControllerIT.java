package guru_springframework.spring_6_rest_mvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru_springframework.spring_6_rest_mvc.entities.BeerOrder;
import guru_springframework.spring_6_rest_mvc.model.BeerOrderCreateDTO;
import guru_springframework.spring_6_rest_mvc.model.BeerOrderDTO;
import guru_springframework.spring_6_rest_mvc.model.BeerOrderLineCreateDTO;
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

import java.util.Set;

import static guru_springframework.spring_6_rest_mvc.controllers.BeerOrderControllerTest.jwtRequestPostProcessor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                        .quantityAllocated(1)
                        .build()))
                .build();

        mockMvc.perform(post(BeerOrderController.BEER_ORDER_PATH)
                        .with(jwtRequestPostProcessor)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerOrderCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

}