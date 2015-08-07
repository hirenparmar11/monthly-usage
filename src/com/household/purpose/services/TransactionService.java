package com.household.purpose.services;

import java.util.List;

import com.household.purpose.models.Transaction;

public interface TransactionService {

	public boolean insert(Transaction transaction);
	public boolean update(Transaction transaction);
	public boolean delete(Transaction transaction);
	public Transaction findById(Long transactionId);
	public List<Transaction> fetchAll();

}
