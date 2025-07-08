package guru_springframework.spring_6_rest_mvc.services;

import guru.springframework.spring6restmvcapi.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Miguel")
                .version(1)
                .createdDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Antonio")
                .version(1)
                .createdDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Lucas")
                .version(1)
                .createdDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        log.debug("Get Customer by Id - in service. Id: " + id.toString());

        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {


        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName(customer.getCustomerName())
                .version(1)
                .createdDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(customerId);
        existing.setCustomerName(customer.getCustomerName());
        existing.setVersion(customer.getVersion());

        return Optional.of(existing);
    }

    @Override
    public Boolean deleteById(UUID customerId) {
        customerMap.remove(customerId);

        return true;
    }

    @Override
    public Optional<CustomerDTO> patchById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(customerId);

        if (StringUtils.hasText(customer.getCustomerName())) {
            existing.setCustomerName(customer.getCustomerName());
        }

        return Optional.of(existing);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }
}

