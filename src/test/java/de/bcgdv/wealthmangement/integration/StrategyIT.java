package de.bcgdv.wealthmangement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bcgdv.wealthmangement.Fixture;
import de.bcgdv.wealthmangement.entity.CustomerEntity;
import de.bcgdv.wealthmangement.model.Strategy;
import de.bcgdv.wealthmangement.repository.CustomerRepository;
import de.bcgdv.wealthmangement.repository.StrategyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StrategyIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StrategyRepository strategyRepository;

    @Test
    void testGetStrategyResource() throws Exception {
        //given
        strategyRepository.save(Fixture.getDefaultCashStrategyEntity());

        //when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/strategies"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void testAssignStrategyResource() throws Exception {
        //given
        customerRepository.save(Fixture.getCustomerEntity());
        strategyRepository.save(Fixture.getDefaultCashStrategyEntity());
        strategyRepository.save(Fixture.getStrategyEntity());

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/strategies?customerId=1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
        ObjectMapper mapper = new ObjectMapper();
        Strategy parsedResponse = mapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                Strategy.class);
        assertEquals(Fixture.getStrategyEntity().getId(),
                parsedResponse.getStrategyId());
        assertEquals(Fixture.getStrategyEntity().getStocksPercentage(),
                parsedResponse.getStocksPercentage());
        assertEquals(Fixture.getStrategyEntity().getCashPercentage(),
                parsedResponse.getCashPercentage());

    }
}
