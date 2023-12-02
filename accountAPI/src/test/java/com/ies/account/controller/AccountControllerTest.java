package com.ies.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ies.account.dto.AccountDto;
import com.ies.account.dto.AccountDtoConverter;
import com.ies.account.dto.CreateAccountRequest;
import com.ies.account.model.Customer;
import com.ies.account.repository.AccountRepository;
import com.ies.account.repository.CustomerRepository;
import com.ies.account.service.AccountService;
import com.ies.account.service.CustomerService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.math.BigDecimal;


@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "server.port=0",
        "command.line.runner.enabled=false"
})
@RunWith(SpringRunner.class)
@DirtiesContext
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountDtoConverter converter;

    private AccountService accountService = new AccountService(accountRepository,customerService, converter);
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
    }

    @Test
    public void testCreateAccount_whenCustomerIdExist_shouldCreateAccountAndReturnAccountDto() throws  Exception{
        Customer customer = customerRepository.save(new Customer("İbrahim Ethem", "Sancar"));
        CreateAccountRequest request = new CreateAccountRequest(customer.getId(), new BigDecimal(100));

        this.mockMvc.perform(post("/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.balance", is(100)))
                .andExpect(jsonPath("$.customer.id", is(customer.getId())))
                .andExpect(jsonPath("$.customer.name", is("İbrahim Ethem")))
                .andExpect(jsonPath("$.customer.surname", is("Sancar")))
                .andExpect(jsonPath("$.transaction", hasSize(1)));


    }

}
