package enigma.customer.service.implementation;

import enigma.customer.dto.Todo;
import enigma.customer.dto.TransactionDTO;
import enigma.customer.model.Customer;
import enigma.customer.model.Transaction;
import enigma.customer.repository.TransactionRepository;
import enigma.customer.service.CustomerService;
import enigma.customer.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;
    private final RestClient restClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CustomerService customerService, RestClient restClient) {
        this.transactionRepository = transactionRepository;
        this.customerService = customerService;
        this.restClient = restClient;
    }

    @Override
    public Transaction create(TransactionDTO transactionDTO) {
        validateTransactionDTO(transactionDTO);

        Customer customer = customerService.getById(transactionDTO.getCustomerId());
        return transactionRepository.save(Transaction.builder()
                .customer(customer)
                .productName(transactionDTO.getProductName())
                .quantity(transactionDTO.getQuantity())
                .price(transactionDTO.getPrice())
                .build()
        );
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public Transaction updateById(Long id, TransactionDTO transactionDTO) {
        validateTransactionDTO(transactionDTO);

        Transaction foundTransaction = getById(id);
        foundTransaction.setProductName(transactionDTO.getProductName());
        foundTransaction.setQuantity(transactionDTO.getQuantity());
        foundTransaction.setPrice(transactionDTO.getPrice());

        return transactionRepository.save(foundTransaction);
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = getById(id);
        transactionRepository.delete(transaction);
    }

    @Override
    public Todo getTodoById(Integer id) {
        return restClient.get()
                .uri("https://jsonplaceholder.typicode.com/todos/{id}", id)
                .retrieve()
                .body(Todo.class);
    }

    @Override
    public List<Todo> getAllTodoFromJsonPlaceHolder() {
        return restClient.get()
                .uri("https://jsonplaceholder.typicode.com/todos")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Todo>>() {
                });
    }

    private void validateTransactionDTO(TransactionDTO transactionDTO) {
        if (transactionDTO.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (transactionDTO.getProductName() == null) {
            throw new IllegalArgumentException("Product name cannot be null");
        }
        if (transactionDTO.getQuantity() == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (transactionDTO.getPrice() == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
    }
}
