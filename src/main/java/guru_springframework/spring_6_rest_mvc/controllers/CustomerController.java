package guru_springframework.spring_6_rest_mvc.controllers;

import guru_springframework.spring_6_rest_mvc.model.CustomerDTO;
import guru_springframework.spring_6_rest_mvc.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomerController {
    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";
    public static final String CUSTOMER_ID = "customerId";

    public final CustomerService customerService;

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<HttpStatus> updateBeerPatchById(@PathVariable(CUSTOMER_ID) UUID customerId, @RequestBody CustomerDTO customer) {

        customerService.patchCustomerById(customerId, customer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<HttpStatus> deleteById(@PathVariable(CUSTOMER_ID) UUID customerId) {

        if (!customerService.deleteById(customerId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<HttpStatus> updateById(@PathVariable(CUSTOMER_ID) UUID customerId, @RequestBody CustomerDTO customer) {

        if (customerService.updateCustomerById(customerId, customer).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<HttpStatus> handlePost(@RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + "/" + savedCustomer.getId());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = CUSTOMER_PATH)
    public List<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable(CUSTOMER_ID) UUID customerId) {

        log.debug("Get Customer by Id - in controller. Id: " + customerId.toString());

        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

}
