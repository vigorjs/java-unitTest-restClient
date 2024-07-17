package enigma.customer.controller;

import enigma.customer.dto.CustomerDTO;
import enigma.customer.model.Customer;
import enigma.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public Customer create(
            @RequestBody CustomerDTO customerDTO
    ) {
        return customerService.create(customerDTO);
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public Customer getById(
            @PathVariable Long id
    ) {
        return customerService.getById(id);
    }

    @PutMapping("/{id}")
    public Customer updateById(
            @PathVariable Long id,
            @RequestBody CustomerDTO customerDTO
    ) {
        return customerService.updateById(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(
            @PathVariable Long id
    ) {
        customerService.deleteById(id);
    }
}
