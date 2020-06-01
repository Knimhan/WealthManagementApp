package de.bcgdv.wealthmangement.service;

import de.bcgdv.wealthmangement.Fixture;
import de.bcgdv.wealthmangement.model.Customer;
import de.bcgdv.wealthmangement.util.CSVReader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvReaderServiceTest {
    public static final String CUSTOMERS_CSV = "/customers.csv";

    @Test
    void testReadCustomerCsv() {
        //when
        List<Customer> customers = CSVReader.readObjects(CUSTOMERS_CSV,Customer.class);

        //then
        assertEquals(2, customers.size());
        Customer expected = Fixture.getCustomer();
        Customer actual = customers.get(0);
        assertEquals(expected.getCustomerId(), actual.getCustomerId());
        assertEquals(expected.getRetirementAge(), actual.getRetirementAge());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getDateOfBirth(), actual.getDateOfBirth());
        assertEquals(expected.getRiskLevel(), actual.getRiskLevel());

    }
}
