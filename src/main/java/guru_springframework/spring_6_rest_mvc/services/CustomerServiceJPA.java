package guru_springframework.spring_6_rest_mvc.services;

import guru.springframework.spring6restmvcapi.model.CustomerDTO;
import guru_springframework.spring_6_rest_mvc.mappers.CustomerMapper;
import guru_springframework.spring_6_rest_mvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CacheManager cacheManager;

    @Cacheable(cacheNames = "customerCache")
    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        log.info("Get Customer by id - in service");

        return Optional.ofNullable(customerMapper
                .customerToCustomerDto(customerRepository.findById(uuid).orElse(null)));
    }

    @Cacheable(cacheNames = "customerListCache")
    @Override
    public List<CustomerDTO> getAllCustomers() {
        log.info("Get All Customers - in service");

        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        if (cacheManager.getCache("customerListCache") != null){
            Objects.requireNonNull(cacheManager.getCache("customerListCache")).clear();
        }

        return customerMapper.customerToCustomerDto(customerRepository
                .save(customerMapper.customerDtoToCustomer(customer)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        clearCache(customerId);

        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setCustomerName(customer.getCustomerName());
            atomicReference.set(Optional.of(customerMapper
                    .customerToCustomerDto(customerRepository.save(foundCustomer))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    private void clearCache(UUID customerId) {
        if (cacheManager.getCache("customerListCache") != null){
            Objects.requireNonNull(cacheManager.getCache("customerListCache")).clear();
        }
        if (cacheManager.getCache("customerCache") != null){
            Objects.requireNonNull(cacheManager.getCache("customerCache")).evict(customerId);
        }
    }

    @Override
    public Boolean deleteById(UUID customerId) {

        clearCache(customerId);

        if(customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerDTO> patchById(UUID customerId, CustomerDTO customer) {

        clearCache(customerId);

        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            if (StringUtils.hasText(customer.getCustomerName())){
                foundCustomer.setCustomerName(customer.getCustomerName());
            }
            atomicReference.set(Optional.of(customerMapper
                    .customerToCustomerDto(customerRepository.save(foundCustomer))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }
}