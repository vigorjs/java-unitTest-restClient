package enigma.customer.repository;

import enigma.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerRepositoryTests {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void CustomerRepository_Create_ReturnCreatedCustomer() {
        Customer customer = Customer.builder()
                .name("customer 1")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        customerRepository.save(customer);

        Customer foundCustomer = customerRepository.findById(customer.getId()).orElse(null);

        assertThat(foundCustomer).isNotNull();
    }

    @Test
    public void CustomerRepository_GetAll_ReturnAllCustomers() {
        Customer customer1 = Customer.builder()
                .name("customer 1")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        Customer customer2 = Customer.builder()
                .name("customer 2")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).isNotNull();
        assertThat(customers).hasSize(2);
    }

    @Test
    public void CustomerRepository_GetById_ReturnAllCustomers() {
        Customer customer1 = Customer.builder()
                .name("customer 1")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        customerRepository.save(customer1);

        Customer customer = customerRepository.findById(customer1.getId()).orElse(null);

        assertThat(customer).isNotNull();
    }

    @Test
    public void CustomerRepository_UpdateById_ReturnUpdatedCustomer() {
        Customer customer1 = Customer.builder()
                .name("customer 1")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        customerRepository.save(customer1);

        Customer foundCustomer = customerRepository.findById(customer1.getId()).orElse(null);
        foundCustomer.setName("updated");
        Customer updatedCustomer = customerRepository.save(foundCustomer);

        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getName()).isEqualTo("updated");
    }

    @Test
    public void CustomerRepository_DeleteById_ReturnNull() {
        Customer customer1 = Customer.builder()
                .name("customer 1")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        customerRepository.save(customer1);

        customerRepository.deleteById(customer1.getId());
        Customer foundCustomer = customerRepository.findById(customer1.getId()).orElse(null);

        assertThat(foundCustomer).isNull();
    }
}
