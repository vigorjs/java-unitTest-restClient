package enigma.customer.service.implementation;

import enigma.customer.dto.CustomerDTO;
import enigma.customer.model.Customer;
import enigma.customer.repository.CustomerRepository;
import enigma.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer create(CustomerDTO customerDTO) {
        validateCustomerDTO(customerDTO);
        return customerRepository.save(Customer.builder()
                .name(customerDTO.getName())
                .birthDate(customerDTO.getBirthDate())
                .build()
        );
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public Customer updateById(Long id, CustomerDTO customerDTO) {
        validateCustomerDTO(customerDTO);
        Customer foundCustomer = getById(id);
        foundCustomer.setName(customerDTO.getName());
        foundCustomer.setBirthDate(customerDTO.getBirthDate());
        return customerRepository.save(foundCustomer);
    }

    @Override
    public void deleteById(Long id) {
        Customer customer = getById(id);
        customerRepository.delete(customer);
    }

    private void validateCustomerDTO(CustomerDTO customerDTO) {
        if (customerDTO.getName() == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (customerDTO.getBirthDate() == null) {
            throw new IllegalArgumentException("Customer birth date cannot be null");
        }
        if (customerDTO.getBirthDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Customer birth date cannot be in the future");
        }
    }
}
