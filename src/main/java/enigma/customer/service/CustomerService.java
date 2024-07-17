package enigma.customer.service;

import enigma.customer.dto.CustomerDTO;
import enigma.customer.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer create(CustomerDTO customerDTO);

    List<Customer> getAll();

    Customer getById(Long id);

    Customer updateById(Long id, CustomerDTO customerDTO);

    void deleteById(Long id);
}
