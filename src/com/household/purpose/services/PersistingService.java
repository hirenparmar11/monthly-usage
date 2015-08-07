package com.household.purpose.services;

import java.util.List;

public interface PersistingService {

	public boolean insert(Object object);
	public boolean update(Object object);
	public boolean delete(Object object);
	public Object findById(Object objectId);
	public List<Object> fetchAll();

}
