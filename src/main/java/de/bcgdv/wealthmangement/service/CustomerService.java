package de.bcgdv.wealthmangement.service;

import de.bcgdv.wealthmangement.entity.CustomerEntity;
import de.bcgdv.wealthmangement.exception.CustomerNotFoundException;
import de.bcgdv.wealthmangement.model.Customer;
import de.bcgdv.wealthmangement.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void saveAll(List<Customer> customers) {
        for (Customer customer : customers) {
            save(customer);
        }
    }

    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        for (CustomerEntity customerEntity: customerRepository.findAll()) {
            Customer customer = new Customer();
            BeanUtils.copyProperties(customerEntity, customer, "customerId", "id");
            customer.setCustomerId(customerEntity.getId());
            customers.add(customer);
        }
        return customers;
    }

    public CustomerEntity save(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customer.getCustomerId());
        BeanUtils.copyProperties(customer, customerEntity, "customerId", "id");
        return save(customerEntity);
    }

    public CustomerEntity save(CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    public CustomerEntity get(Integer id) {
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(id);
        if (!optionalCustomerEntity.isPresent()) throw new CustomerNotFoundException("Customer not found");
        return optionalCustomerEntity.get();
    }
}
