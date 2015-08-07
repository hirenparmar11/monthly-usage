package com.household.purpose.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.household.purpose.models.PaymentMethod;
import com.household.purpose.models.PaymentMethod.PaymentType;
import com.household.purpose.models.Transaction;
import com.household.purpose.services.TransactionService;

public class TransactionControllerTest {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private TransactionController controller;
	
	@Mock
	private TransactionService service;
	
	public TransactionControllerTest() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	public Map<Long, Transaction> dummyTransactions() {
		Map<Long, Transaction> transactions = new HashMap<Long, Transaction>();
		
		Transaction t1 = new Transaction();
		PaymentMethod m1 = new PaymentMethod();
		String[] items1 = {"abc", "xyz", "pqr"};
		
		m1.setAccountNumber("4083024802348202");
		m1.setBankName("Bank Of America");
		m1.setReadableName("BofACC");
		m1.setType(PaymentType.CREDIT_CARD);
		
		t1.setTransactionId(1L);
		t1.setTimestamp(new Date().getTime());
		t1.setAmount(540.34);
		t1.setPayment(m1);
		t1.setItems(Arrays.asList(items1));
		
		Transaction t2 = new Transaction();
		PaymentMethod m2 = new PaymentMethod();
		String[] items2 = {"xbox", "games", "dvds"};
		
		m2.setAccountNumber("4083024802341231");
		m2.setBankName("Bank Of America");
		m2.setReadableName("BofADC");
		m2.setType(PaymentType.DEBIT_CARD);
		
		t2.setTransactionId(2L);
		t2.setTimestamp(new Date().getTime());
		t2.setAmount(10.56);
		t2.setPayment(m2);
		t2.setItems(Arrays.asList(items2));
		
		Transaction t3 = new Transaction();
		t3.setTransactionId(3L);
		t3.setTimestamp(new Date().getTime());
		t3.setAmount(29.32);
		t3.setPayment(m1);
		t3.setItems(Arrays.asList(items1));
		
		transactions.put(t1.getTransactionId(), t1);
		transactions.put(t2.getTransactionId(), t2);
		transactions.put(t3.getTransactionId(), t3);
		
		return transactions;
	}
	
	@Test
	public void testFetchAllTransactions() throws Exception {
				
		Mockito.when(service.fetchAll()).thenReturn(new ArrayList<Transaction>(dummyTransactions().values()));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/fetchAll"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].transactionId", Matchers.is(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].payment.type", Matchers.is(PaymentType.CREDIT_CARD.toString())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].payment.bankName", Matchers.is("Bank Of America")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].payment.accountNumber", Matchers.is("4083024802348202")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].payment.readableName", Matchers.is("BofACC")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].items[0]", Matchers.is("abc")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].items[1]", Matchers.is("xyz")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].items[2]", Matchers.is("pqr")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[1].transactionId", Matchers.is(2)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[1].payment.type", Matchers.is(PaymentType.DEBIT_CARD.toString())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[1].payment.bankName", Matchers.is("Bank Of America")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[1].payment.accountNumber", Matchers.is("4083024802341231")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[1].payment.readableName", Matchers.is("BofADC")))	
			.andExpect(MockMvcResultMatchers.jsonPath("$.[1].items[0]", Matchers.is("xbox")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[1].items[1]", Matchers.is("games")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[1].items[2]", Matchers.is("dvds")))			
			.andExpect(MockMvcResultMatchers.jsonPath("$.[2].transactionId", Matchers.is(3)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[2].payment.type", Matchers.is(PaymentType.CREDIT_CARD.toString())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[2].payment.bankName", Matchers.is("Bank Of America")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[2].payment.accountNumber", Matchers.is("4083024802348202")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[2].payment.readableName", Matchers.is("BofACC")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[2].items[0]", Matchers.is("abc")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[2].items[1]", Matchers.is("xyz")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[2].items[2]", Matchers.is("pqr")))			
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testFetchAllTransactionsNotFound() throws Exception {
		
		Mockito.when(service.fetchAll()).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/fetchAll"))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testFetchTransaction() throws Exception {
		Long fetchValue = 2L;
		Long transactionId = ((Transaction)dummyTransactions().get(fetchValue)).getTransactionId();
		
		Mockito.when(service.findById(fetchValue)).thenReturn(dummyTransactions().get(fetchValue));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/fetch/" + fetchValue))
			.andExpect(MockMvcResultMatchers.jsonPath("$.transactionId", Matchers.anyOf(Matchers.equalTo((Number) transactionId), Matchers.equalTo((Number)transactionId.intValue()))))
			.andExpect(MockMvcResultMatchers.jsonPath("$.payment.type", Matchers.is(((Transaction)dummyTransactions().get(fetchValue)).getPayment().getType().toString())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.payment.bankName", Matchers.is(((Transaction)dummyTransactions().get(fetchValue)).getPayment().getBankName())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.payment.accountNumber", Matchers.is(((Transaction)dummyTransactions().get(fetchValue)).getPayment().getAccountNumber())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.payment.readableName", Matchers.is(((Transaction)dummyTransactions().get(fetchValue)).getPayment().getReadableName())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.items[0]", Matchers.is(((Transaction)dummyTransactions().get(fetchValue)).getItems().get(0))))
			.andExpect(MockMvcResultMatchers.jsonPath("$.items[1]", Matchers.is(((Transaction)dummyTransactions().get(fetchValue)).getItems().get(1))))
			.andExpect(MockMvcResultMatchers.jsonPath("$.items[2]", Matchers.is(((Transaction)dummyTransactions().get(fetchValue)).getItems().get(2))))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testFetchTransactionNotFound() throws Exception {
		Long fetchValue = 3L;
		
		Mockito.when(service.findById(fetchValue)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/fetch/" + fetchValue))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}


	@Test
	public void testCreateTransaction() throws Exception {
		
		Mockito.when(service.insert(Mockito.any(Transaction.class))).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/transactions/create")
				.content("{\"transactionId\":\"1\", \"timestamp\":"+ new Date().getTime() +", \"payment\":{\"type\":\"CREDIT_CARD\", \"bankName\":\"Bank Of America\", \"accountNumber\" : \"4083024802348202\", \"readableName\" :\"BofACC\"}, \"amount\" : \"10.56\", \"items\":[\"onions\", \"potatoes\"]}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.transactionId", Matchers.is(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.payment.type", Matchers.is("CREDIT_CARD")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.payment.bankName", Matchers.is("Bank Of America")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.payment.accountNumber", Matchers.is("4083024802348202")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.payment.readableName", Matchers.is("BofACC")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.items[0]", Matchers.is("onions")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.items[1]", Matchers.is("potatoes")))
			.andExpect(MockMvcResultMatchers.status().isOk());

	}
	
	@Test
	public void testCreateTransactionNotFound() throws Exception {	
		Mockito.when(service.insert(Mockito.any(Transaction.class))).thenReturn(false);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/transactions/create")
				.content("{\"transactionId\":\"1\", \"timestamp\":"+ new Date().getTime() +", \"payment\":{\"type\":\"CREDIT_CARD\", \"bankName\":\"Bank Of America\", \"accountNumber\" : \"4083024802348202\", \"readableName\" :\"BofACC\"}, \"amount\" : \"10.56\", \"items\":[\"onions\", \"potatoes\"]}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isNotModified());
	}
}
