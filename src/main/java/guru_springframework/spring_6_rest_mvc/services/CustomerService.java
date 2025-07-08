package guru_springframework.spring_6_rest_mvc.services;


import guru.springframework.spring6restmvcapi.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Optional<CustomerDTO> getCustomerById(UUID uuid);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer);

    Boolean deleteById(UUID customerId);

    Optional<CustomerDTO> patchById(UUID customerId, CustomerDTO customer);

}