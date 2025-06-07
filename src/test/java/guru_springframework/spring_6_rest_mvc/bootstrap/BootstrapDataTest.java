package guru_springframework.spring_6_rest_mvc.bootstrap;

import guru_springframework.spring_6_rest_mvc.repositories.BeerOrderRepository;
import guru_springframework.spring_6_rest_mvc.repositories.BeerRepository;
import guru_springframework.spring_6_rest_mvc.repositories.CustomerRepository;
import guru_springframework.spring_6_rest_mvc.services.BeerCsvService;
import guru_springframework.spring_6_rest_mvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    BeerCsvService beerCsvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository, beerOrderRepository, beerCsvService);
    }

    @Test
    void TestRun() throws Exception {
        bootstrapData.run();

        assertThat(beerRepository.count()).isGreaterThan(1);
        assertThat(customerRepository.count()).isGreaterThan(1);
        assertThat(beerOrderRepository.count()).isGreaterThan(1);
    }
}