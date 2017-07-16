package com.mayer.mvc.test;

import java.util.List;

import org.junit.Test;

import com.mayer.mvc.dao.CriteriaCustomer;
import com.mayer.mvc.dao.CustomerDAO;
import com.mayer.mvc.dao.impl.CustomerDAOJdbcImpl;
import com.mayer.mvc.domain.Customer;

public class CustomerDAOJdbcImplTest {
	
	private CustomerDAO customerDAO = new CustomerDAOJdbcImpl();
	
	@Test
	public void testGetForListWithCriteriaCustomer(){
		CriteriaCustomer cc = new CriteriaCustomer("k", null, null);
		List<Customer> customers = customerDAO.getForListWithCriteriaCustomer(cc);
		System.out.println(customers);
	}

	@Test
	public void testGetAll() {
		List<Customer> customers = customerDAO.getAll();
		System.out.println(customers);
	}

	@Test
	public void testSave() {
		Customer customer = new Customer();
		customer.setAddress("ShangHai");
		customer.setName("Jerry");
		customer.setPhone("13534361357");
		
		customerDAO.save(customer);
	}

	@Test
	public void testGetInteger() {
		Customer cust = customerDAO.get(1);
		System.out.println(cust);
	}

	@Test
	public void testDelete() {
		customerDAO.delete(1);
	}

	@Test
	public void testGetCountWithName() {
		long count = customerDAO.getCountWithName("ABC");
		System.out.println(count);
	}

}
