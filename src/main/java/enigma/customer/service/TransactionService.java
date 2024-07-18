package enigma.customer.service;

import enigma.customer.dto.Todo;
import enigma.customer.dto.TransactionDTO;
import enigma.customer.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction create(TransactionDTO transactionDTO);

    List<Transaction> getAll();

    Transaction getById(Long id);

    Transaction updateById(Long id, TransactionDTO transactionDTO);

    void deleteById(Long id);


    //practice rest client
    Todo getTodoById(Integer id);

    List<Todo> getAllTodoFromJsonPlaceHolder();
}
