package ru.hf.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hf.model.Transaction;
import ru.hf.service.TransactionService;

import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static ru.hf.util.TransactionMock.getDefaultTransaction;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-dev.properties")
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void create() throws Exception {
        File requestFile = new ClassPathResource("transactions/create-good-rq.json").getFile();
        String requestText = new String(Files.readAllBytes(requestFile.toPath()));
        String responseText = "Transactions have been created successfully";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/transactions/")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestText)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(responseText, response.getContentAsString());
    }

    @Test
    public void createException() throws Exception {
        File requestFile = new ClassPathResource("transactions/create-exception-rq.json").getFile();
        String requestText = new String(Files.readAllBytes(requestFile.toPath()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/transactions/")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestText)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void update() throws Exception {
        Transaction transactionMock = getDefaultTransaction();
        given(transactionService.getById(transactionMock.getId())).willReturn(transactionMock);
        given(transactionService.save(transactionMock)).willReturn(transactionMock);
        String responseText = "Transaction [" + transactionMock.getId() + "] has been updated successfully";

        File requestFile = new ClassPathResource("transactions/update-good-rq.json").getFile();
        String requestText = new String(Files.readAllBytes(requestFile.toPath()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/transactions/")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestText)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(responseText, response.getContentAsString());
    }

    @Test
    public void updateException() throws Exception {
        Transaction transactionMock = getDefaultTransaction();
        given(transactionService.getById(transactionMock.getId())).willReturn(transactionMock);
        given(transactionService.save(any(Transaction.class))).willReturn(transactionMock);

        File requestFile = new ClassPathResource("transactions/update-exception-rq.json").getFile();
        String requestText = new String(Files.readAllBytes(requestFile.toPath()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/transactions/")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestText)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void delete() throws Exception {
        Transaction transactionMock = getDefaultTransaction();
        given(transactionService.getById(transactionMock.getId())).willReturn(transactionMock);
        given(transactionService.save(any(Transaction.class))).willReturn(transactionMock);
        String responseText = "Transaction [" + transactionMock.getId() + "] has been deleted successfully";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/transactions/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(responseText, response.getContentAsString());
    }

    @Test
    public void deleteNotFound() throws Exception {
        Transaction transactionMock = getDefaultTransaction();
        given(transactionService.getById(transactionMock.getId())).willReturn(transactionMock);
        given(transactionService.save(any(Transaction.class))).willReturn(transactionMock);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/transactions/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
}
