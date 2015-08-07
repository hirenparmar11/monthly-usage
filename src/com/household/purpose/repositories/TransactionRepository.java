package com.household.purpose.repositories;

import java.util.List;

import com.household.purpose.models.Transaction;

public interface  TransactionRepository {

	public boolean insert(Transaction transtion);
	public boolean update(Transaction transtion);
	public boolean delete(Transaction transtion);
	public Transaction findById(Transaction transtionId);
	public List<Transaction> fetchAll();
}
