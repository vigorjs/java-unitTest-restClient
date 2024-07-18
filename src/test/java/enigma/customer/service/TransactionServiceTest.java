package enigma.customer.service;

import enigma.customer.dto.TransactionDTO;
import enigma.customer.model.Customer;
import enigma.customer.model.Transaction;
import enigma.customer.repository.TransactionRepository;
import enigma.customer.service.implementation.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;
    private TransactionDTO dto;
    private Customer customer;

    @BeforeEach
    void setUp(){
        // instance transaction
        transaction = new Transaction();

        // instance transactionDTO
        dto = new TransactionDTO();
        dto.setProductName("Ozan");
        dto.setPrice(1000);
        dto.setQuantity(69);
        dto.setCustomerId(1L);

        // instance Customer
        customer = new Customer();
        customer.setId(1L);
        customer.setName("OzanCustomer");
        customer.setBirthDate(LocalDate.now());
    }

    @Test
    public void getAllTransaction(){
        // given
        List<Transaction> transactions = new ArrayList<>(List.of(new Transaction(), new Transaction()));

        when(transactionRepository.findAll()).thenReturn(transactions);

        // when
        List<Transaction> result = transactionService.getAll();

        // then
        assertEquals(result, transactions);
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    public void createTransaction_success(){
        // given

        when(customerService.getById(any(Long.class))).thenReturn(customer);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // when
        Transaction result = transactionService.create(dto);

        // then
        assertEquals(result, transaction);
        verify(customerService, times(1)).getById(1L);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void updateTransaction_success(){
        // given
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // when
        Transaction result = transactionService.updateById(1L, dto);

        // then
        assertEquals(result, transaction);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void createTransaction_fail(){
        // Given
        Long nonExistCustomerId = 4821L;
        dto.setCustomerId(nonExistCustomerId);
        String expectedMessage = "User not Found";

        when(customerService.getById(nonExistCustomerId)).thenThrow(new RuntimeException(expectedMessage));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                                        transactionService.create(dto);
                                    });
        assertEquals(expectedMessage, exception.getMessage());
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    public void deleteTransaction_success(){
        //Given
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction)).thenReturn(Optional.empty());
        doNothing().when(transactionRepository).delete(transaction);

        // When
        transactionService.deleteById(1L);

        // Then
        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).delete(transaction);

        Optional<Transaction> deletedTransaction = transactionRepository.findById(1L);
        assertThat(deletedTransaction).isEmpty();

    }



}
