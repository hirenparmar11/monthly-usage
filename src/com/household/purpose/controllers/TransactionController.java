package com.household.purpose.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.household.purpose.models.Transaction;
import com.household.purpose.services.PersistingService;

@Controller
@RequestMapping(value="/transactions")
public class TransactionController {

	private PersistingService service;
	
	public TransactionController(PersistingService service) {
		this.service = service;
	}
	
	@RequestMapping(value="/fetchAll", method=RequestMethod.GET) 
	public ResponseEntity<List<Object>> fetchAllTransactions() {
		List<Object> transactions = service.fetchAll();
		if(transactions != null && transactions.size() >0) {
			return new ResponseEntity<List<Object>>(transactions, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Object>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/fetch/{transactionId}", method=RequestMethod.GET) 
	public ResponseEntity<Transaction> fetchAllTransactions(@PathVariable Long transactionId) {
		Transaction transaction = (Transaction) service.findById(transactionId);
		if(transaction !=null) {
			return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
		} else {
			return new ResponseEntity<Transaction>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
		if(service.insert(transaction)) {
			return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
		} else {
			return new ResponseEntity<Transaction>(HttpStatus.NOT_MODIFIED);
		}
	}
}
