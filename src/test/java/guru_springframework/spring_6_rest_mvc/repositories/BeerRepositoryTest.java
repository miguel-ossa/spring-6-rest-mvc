package guru_springframework.spring_6_rest_mvc.repositories;

import guru_springframework.spring_6_rest_mvc.entities.Beer;
import guru_springframework.spring_6_rest_mvc.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("New Beer")
                .beerStyle(BeerStyle.ALE)
                .upc("12234")
                .price(new BigDecimal("11.11"))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}