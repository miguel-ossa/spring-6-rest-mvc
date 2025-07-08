package guru_springframework.spring_6_rest_mvc.mappers;

import guru.springframework.spring6restmvcapi.model.CustomerDTO;
import guru_springframework.spring_6_rest_mvc.entities.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
