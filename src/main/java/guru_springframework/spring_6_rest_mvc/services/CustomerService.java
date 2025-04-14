package guru_springframework.spring_6_rest_mvc.services;

import guru_springframework.spring_6_rest_mvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer);

    Boolean deleteById(UUID customerId);

    void patchCustomerById(UUID customerId, CustomerDTO customer);
}
