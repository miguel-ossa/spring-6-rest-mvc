package guru_springframework.spring_6_rest_mvc.mappers;

import guru_springframework.spring_6_rest_mvc.entities.Customer;
import guru_springframework.spring_6_rest_mvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
