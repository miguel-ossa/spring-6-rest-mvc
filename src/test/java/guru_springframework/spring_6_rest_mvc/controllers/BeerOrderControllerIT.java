package guru_springframework.spring_6_rest_mvc.controllers;

import guru_springframework.spring_6_rest_mvc.entities.BeerOrder;
import guru_springframework.spring_6_rest_mvc.model.BeerOrderDTO;
import guru_springframework.spring_6_rest_mvc.repositories.BeerOrderRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RecordApplicationEvents
@SpringBootTest
class BeerOrderControllerIT {

    @Autowired
    BeerOrderController beerOrderController;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

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

        assertThat(dtos.getContent().size()).isEqualTo(6);
    }

    @Transactional
    @Test
    void testGetBeerOrderById() {
        BeerOrder beerOrder = beerOrderRepository.findAll().getFirst();

        BeerOrderDTO dto = beerOrderController.getBeerOrderById(beerOrder.getId());

        assertThat(dto).isNotNull();
    }
}