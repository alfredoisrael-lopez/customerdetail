package com.ibm.assets.microclimate.customer.dao;

import com.ibm.assets.microclimate.customer.domain.Customer;

public interface CustomerDao {
	public Customer getCustomer(String clientNumber);
}
