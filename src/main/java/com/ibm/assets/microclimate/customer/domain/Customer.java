package com.ibm.assets.microclimate.customer.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customer")
public class Customer implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String clientNumber;
	private List<String> messages;
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "clientNumber")
	public String getClientNumber() {
		return clientNumber;
	}
	
	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}
	
	@XmlElement(name = "messages")
	public List<String> getMessages() {
		return messages;
	}
	
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public Customer(String name, String clientNumber, List<String> messages) {
		super();
		this.name = name;
		this.clientNumber = clientNumber;
		this.messages = messages;
	}

	public Customer() {
		super();
		this.name = "";
		this.clientNumber = "";
		this.messages = new ArrayList<String>();
	}
	
	 
}
