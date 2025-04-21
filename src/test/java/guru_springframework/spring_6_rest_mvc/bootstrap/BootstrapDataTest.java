package guru_springframework.spring_6_rest_mvc.bootstrap;

import guru_springframework.spring_6_rest_mvc.repositories.BeerRepository;
import guru_springframework.spring_6_rest_mvc.repositories.CustomerRepository;
import guru_springframework.spring_6_rest_mvc.services.BeerCsvService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
@SpringBootTest
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCsvService beerCsvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvService);
    }

    @Test
    void TestRun() throws Exception {
        bootstrapData.run();

        assertThat(beerRepository.count()).isGreaterThan(1);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}