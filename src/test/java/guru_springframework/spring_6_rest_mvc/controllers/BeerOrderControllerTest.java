package guru_springframework.spring_6_rest_mvc.controllers;

import guru_springframework.spring_6_rest_mvc.config.SpringSecConfig;
import guru_springframework.spring_6_rest_mvc.model.BeerOrderCreateDTO;
import guru_springframework.spring_6_rest_mvc.model.BeerOrderDTO;
import guru_springframework.spring_6_rest_mvc.model.BeerOrderLineCreateDTO;
import guru_springframework.spring_6_rest_mvc.services.*;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerOrderController.class)
@Import(SpringSecConfig.class)
class BeerOrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerOrderService beerOrderService;

    BeerOrderServiceImpl beerOrderServiceImpl;

    @MockitoBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @MockitoBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        beerOrderServiceImpl = new BeerOrderServiceImpl();
        beerServiceImpl = new BeerServiceImpl();
        customerServiceImpl = new CustomerServiceImpl();
    }

    public static final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRequestPostProcessor =
            jwt().jwt(jwt -> jwt.claims(claims -> {
                        List<String> scopes = new ArrayList<>();
                        scopes.add("message-read");
                        scopes.add("message-write");
                        claims.put("scope", scopes);
                    })
                    .subject("messaging-client")
                    .notBefore(Instant.now().minusSeconds(5L)));

    @Test
    void testListBeerOrders() throws Exception {
        given(beerOrderService.listBeerOrders(any(), any())).willReturn(beerOrderServiceImpl.listBeerOrders(1, 25));

        mockMvc.perform(get(BeerOrderController.BEER_ORDER_PATH)
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(2)));
    }

    @Test
    void testGetBeerOrderById() throws Exception {
        BeerOrderDTO order = beerOrderServiceImpl.listBeerOrders(1, 25).getContent().getFirst();

        given(beerOrderService.getBeerOrderById(order.getId())).willReturn(Optional.of(order));

        mockMvc.perform(get(BeerOrderController.BEER_ORDER_PATH_ID, order.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(order.getId().toString())));
    }

    @Test
    void testCreateNewBeerOrder() throws Exception {
        val beer = beerServiceImpl.listBeers(any(), any(), any(), any(), any()).getContent().getFirst();
        val customer = customerServiceImpl.getAllCustomers().getFirst();
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