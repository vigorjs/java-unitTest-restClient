package enigma.customer.service;

import enigma.customer.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer create(Customer customer);

    List<Customer> getAll();

    Customer getById(Long id);

    Customer updateById(Long id, Customer updatedCustomer);

    void deleteById(Long id);
}
