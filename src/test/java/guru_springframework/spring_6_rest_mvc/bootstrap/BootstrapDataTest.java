package guru_springframework.spring_6_rest_mvc.bootstrap;

import guru_springframework.spring_6_rest_mvc.entities.Beer;
import guru_springframework.spring_6_rest_mvc.entities.Customer;
import guru_springframework.spring_6_rest_mvc.repositories.BeerRepository;
import guru_springframework.spring_6_rest_mvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository);
    }

    @Test
    void TestRun() throws Exception {
        bootstrapData.run();

        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);

        Beer beer = beerRepository.findAll().getFirst();
        System.out.println("beer Id: " + beer.getId() + ", beerVersion: " + beer.getVersion());

        Customer customer = customerRepository.findAll().getFirst();
        System.out.println("customerId: " + customer.getId() + ", customerVersion: " + customer.getVersion());
    }
}