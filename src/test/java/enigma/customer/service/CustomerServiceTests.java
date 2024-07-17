package enigma.customer.service;

import enigma.customer.dto.CustomerDTO;
import enigma.customer.model.Customer;
import enigma.customer.repository.CustomerRepository;
import enigma.customer.service.implementation.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void CustomerService_Create_ReturnCreatedCustomer() {
        Customer customer = mock(Customer.class);
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("customer 1")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);
        Customer savedCustomer = customerService.create(customerDTO);

        assertThat(savedCustomer).isNotNull();
    }

    @Test
    public void CustomerService_GetAll_ReturnAllCustomers() {
        List<Customer> customers = mock(List.class);

        when(customerRepository.findAll())
                .thenReturn(customers);
        List<Customer> foundCustomers = customerService.getAll();

        assertThat(foundCustomers).isNotNull();
    }

    @Test
    public void CustomerService_GetById_ReturnCustomer() {
        Customer customer = mock(Customer.class);

        when(customerRepository.findById(any(Long.class)))
                .thenReturn(Optional.ofNullable(customer));
        Customer foundCustomer = customerService.getById(1L);

        assertThat(foundCustomer).isNotNull();
    }

    @Test
    public void CustomerService_UpdateById_ReturnUpdatedCustomer() {
        Customer customer = mock(Customer.class);
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("customer 1")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        when(customerRepository.findById(any(Long.class)))
                .thenReturn(Optional.ofNullable(customer));
        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);
        Customer updatedCustomer = customerService.updateById(1L, customerDTO);

        assertThat(updatedCustomer).isNotNull();
    }

    @Test
    public void CustomerService_DeleteById_Void() {
        Customer customer = mock(Customer.class);

        when(customerRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(customer));
        assertAll(() -> customerService.deleteById(1L));
    }

    @Test
    public void CustomerService_CreateWithNullName_ThrowsIllegalArgumentException() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name(null)
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        assertThrows(
                IllegalArgumentException.class,
                () -> customerService.create(customerDTO)
        );
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void CustomerService_CreateWithNullDate_ThrowsIllegalArgumentException() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("customer 1")
                .birthDate(null)
                .build();

        assertThrows(
                IllegalArgumentException.class,
                () -> customerService.create(customerDTO)
        );
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void CustomerService_UpdateWithIdNotExists_ThrowsIllegalArgumentException() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("updated")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();


        when(customerRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> customerService.updateById(1L, customerDTO)
        );
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void CustomerService_GetOneNonExistentId_ThrowsIllegalArgumentException() {
        when(customerRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> customerService.getById(1L)
        );
    }

    @Test
    public void CustomerService_DeleteByNonExistentId_ThrowsIllegalArgumentException() {
        when(customerRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> customerService.deleteById(1L)
        );
        verify(customerRepository, never()).deleteById(any(Long.class));
    }
}
