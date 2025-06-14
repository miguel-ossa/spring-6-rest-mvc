package guru_springframework.spring_6_rest_mvc.controllers;

import guru_springframework.spring_6_rest_mvc.config.SpringSecConfig;
import guru_springframework.spring_6_rest_mvc.model.BeerOrderDTO;
import guru_springframework.spring_6_rest_mvc.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerOrderController.class)
@Import(SpringSecConfig.class)
class BeerOrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BeerOrderService beerOrderService;

    BeerOrderServiceImpl beerOrderServiceImpl;

    BeerServiceImpl beerServiceImpl;

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

}