package enigma.customer.service.implementation;

import enigma.customer.model.Customer;
import enigma.customer.repository.CustomerRepository;
import enigma.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer updateById(Long id, Customer updatedCustomer) {
        Customer foundCustomer = getById(id);
        if (foundCustomer == null) {
            return null;
        }
        foundCustomer.setName(updatedCustomer.getName());
        foundCustomer.setBirthDate(updatedCustomer.getBirthDate());
        return customerRepository.save(foundCustomer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
