package com.ibm.assets.microclimate.customer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.assets.microclimate.customer.domain.Customer;

public class CustomerDaoImpl implements CustomerDao {

	private static final Log log = LogFactory.getLog(CustomerDaoImpl.class);

	private static final String DS_NAME = "jdbc/customers";
	private static final String GET_CUSTOMER = "SELECT CLIENT_NUMBER, NAME FROM customers.CUSTOMERS WHERE CLIENT_NUMBER = ?";
	private static final String GET_CUSTOMER_MESSAGES = "SELECT MESSAGES FROM customers.CUSTOMER_MESSAGES WHERE CUSTOMER_NUMBER = ?";

	protected Connection connection;

	private void initConnection() {
		try {
			Context context = new InitialContext();
			DataSource datasource = (DataSource) context.lookup(DS_NAME);
			connection = datasource.getConnection();
		} catch (NamingException | SQLException e) {
			log.error("Error", e);
		}
	}

	@Override
	public Customer getCustomer(String clientNumber) {
		initConnection();

		Customer customer = new Customer();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(GET_CUSTOMER);
			preparedStatement.setString(1, clientNumber);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				if (log.isInfoEnabled()) {
					log.info("Client Number --> " + resultSet.getString("CLIENT_NUMBER"));
					log.info("Name --> " + resultSet.getString("NAME"));
				}
				customer.setClientNumber(resultSet.getString("CLIENT_NUMBER"));
				customer.setName(resultSet.getString("NAME"));
				getMessages(clientNumber, customer);
			}

		} catch (SQLException e) {
			log.fatal("getCustomer --> ", e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				log.fatal("getCustomer finally --> ", e);
			}
		}


		return customer;
	}

	private void getMessages(String clientNumber, Customer customer) throws SQLException {
		PreparedStatement preparedStatementMessages = connection.prepareStatement(GET_CUSTOMER_MESSAGES);
		preparedStatementMessages.setString(1, clientNumber);
		ResultSet resultSetMessages = preparedStatementMessages.executeQuery();
		List<String> messages = new ArrayList<String>();

		while (resultSetMessages.next()) {
			if (log.isInfoEnabled()) {
				log.info("Messages --> " + resultSetMessages.getString("MESSAGES"));
			}
			messages.add(resultSetMessages.getString("MESSAGES"));
		}

		customer.setMessages(messages);
	}

}
