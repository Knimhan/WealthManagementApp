package de.bcgdv.wealthmangement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bcgdv.wealthmangement.Fixture;
import de.bcgdv.wealthmangement.model.CustomerPortfolio;
import de.bcgdv.wealthmangement.repository.CustomerRepository;
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
public class CustomerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testGetCustomerResource() throws Exception {
        //given
        customerRepository.save(Fixture.getCustomerEntity());

        //when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void testRebalanceStrategyResource() throws Exception {
        //given
        customerRepository.save(Fixture.getCustomerEntity());

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/customers/1/rebalance")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
        ObjectMapper mapper = new ObjectMapper();
        CustomerPortfolio parsedResponse = mapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                CustomerPortfolio.class);
        assertEquals(Fixture.getCustomerPortfolio().getCash(), parsedResponse.getCash());
    }
}
