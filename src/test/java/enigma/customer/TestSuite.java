package enigma.customer;

import enigma.customer.controller.TransactionController;
import enigma.customer.controller.TransactionControllerTest;
import enigma.customer.repository.CustomerRepositoryTests;
import enigma.customer.service.CustomerServiceTests;
import enigma.customer.service.TransactionServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CustomerRepositoryTests.class,
        CustomerServiceTests.class,
        TransactionServiceTest.class,
        TransactionControllerTest.class
})
public class TestSuite {
}
