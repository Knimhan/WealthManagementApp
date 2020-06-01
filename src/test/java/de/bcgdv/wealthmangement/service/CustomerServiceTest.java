package de.bcgdv.wealthmangement.service;

import de.bcgdv.wealthmangement.Fixture;
import de.bcgdv.wealthmangement.entity.CustomerEntity;
import de.bcgdv.wealthmangement.exception.CustomerNotFoundException;
import de.bcgdv.wealthmangement.model.Customer;
import de.bcgdv.wealthmangement.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new CustomerService(customerRepository);
    }

    @Test
    public void testSaveCustomer() {
        //given
        Customer customer = Fixture.getCustomer();

        //when
        CustomerEntity customerEntity = classUnderTest.save(customer);

        //then
        assertEquals(customer.getCustomerId(), customerEntity.getId());
        assertEquals(customer.getEmail(), customerEntity.getEmail());
        assertEquals(customer.getDateOfBirth(), customerEntity.getDateOfBirth());
        assertEquals(customer.getRiskLevel(), customerEntity.getRiskLevel());
        assertEquals(customer.getRetirementAge(), customerEntity.getRetirementAge());
    }


    @Test
    void testCustomerNotFound() {
        Throwable exception = assertThrows(CustomerNotFoundException.class,
                () -> classUnderTest.get(100));

        assertEquals("Customer not found", exception.getMessage());
    }
}
