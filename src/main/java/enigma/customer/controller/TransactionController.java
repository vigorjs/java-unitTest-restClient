package enigma.customer.controller;

import enigma.customer.dto.Todo;
import enigma.customer.dto.TransactionDTO;
import enigma.customer.model.Transaction;
import enigma.customer.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public Transaction create(
            @RequestBody TransactionDTO transactionDTO
    ) {
        return transactionService.create(transactionDTO);
    }

    @GetMapping
    public List<Transaction> getAll() {
        return transactionService.getAll();
    }

    @GetMapping("/{id}")
    public Transaction getById(
            @PathVariable Long id
    ) {
        return transactionService.getById(id);
    }

    @PutMapping("/{id}")
    public Transaction updateById(
            @PathVariable Long id,
            @RequestBody TransactionDTO transactionDTO
    ) {
        return transactionService.updateById(id, transactionDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(
            @PathVariable Long id
    ) {
        transactionService.deleteById(id);
    }

    @GetMapping("/todos")
    public List<Todo> test(){
        return transactionService.getAllTodoFromJsonPlaceHolder();
    }

    @GetMapping("/todos/{id}")
    public Todo test(@PathVariable Integer id){
        return transactionService.getTodoById(id);
    }
}
