package enigma.customer.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import enigma.customer.dto.TransactionDTO;
import enigma.customer.model.Customer;
import enigma.customer.model.Transaction;
import enigma.customer.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    private Transaction transaction;
    private TransactionDTO transactionDTO;
    private Customer customer;

    @BeforeEach
    void setUp(){
        customer = Customer.builder()
                .id(1L)
                .name("Kim Jong")
                .birthDate(LocalDate.now())
                .build();

        transaction = Transaction.builder()
                .id(1L)
                .price(1000)
                .quantity(5)
                .productName("gayung")
                .build();

        transactionDTO = TransactionDTO.builder()
                .customerId(1L)
                .price(1000)
                .quantity(5)
                .productName("gayung")
                .build();
    }

    @Test
    public void createTransaction() throws Exception {
        when(transactionService.create(any(TransactionDTO.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transaction.getId()))
                .andExpect(jsonPath("$.price").value(transaction.getPrice()))
                .andExpect(jsonPath("$.quantity").value(transaction.getQuantity()))
                .andExpect(jsonPath("$.productName").value(transaction.getProductName()));

        verify(transactionService, times(1)).create(any(TransactionDTO.class));
    }

    @Test
    void getAllTransaction() throws Exception{
        List<Transaction> transactions = new ArrayList<>(List.of(transaction, transaction));

        when(transactionService.getAll()).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(transactionService, times(1)).getAll();
    }

    @Test
    void getByIdTransaction() throws Exception{

        when(transactionService.getById(1L)).thenReturn(transaction);

        mockMvc.perform(get("/api/transactions/" + transaction.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transaction.getId()))
                .andExpect(jsonPath("$.price").value(transaction.getPrice()))
                .andExpect(jsonPath("$.quantity").value(transaction.getQuantity()))
                .andExpect(jsonPath("$.productName").value(transaction.getProductName()));

        verify(transactionService, times(1)).getById(1L);
    }

    @Test
    public void deleteTransaction() throws Exception{
        doNothing().when(transactionService).deleteById(transaction.getId());

        mockMvc.perform(delete("/api/transactions/" + transaction.getId()))
                .andExpect(status().isOk());

        verify(transactionService, times(1)).deleteById(1L);
    }
}
