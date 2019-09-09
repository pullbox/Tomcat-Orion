package com.orion.extra;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
import com.orion.mockdata.generate.utils.RandomUtil; 
import com.orion.shoppingcart.domain.Customer; 
import com.orion.shoppingcart.domain.Order; 
import com.orion.shoppingcart.manager.CustomerManager; 
import com.orion.shoppingcart.manager.OrderManager; 
import com.orion.shoppingcart.util.ExceptionListReader; 
 
public class TestClass { 
 
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	 
	@SuppressWarnings("unchecked") 
	public void  METHOD_NAME (String customerNumber, String orderNumber, String shipmentDetails, String shipNumber) {	 
		CustomerManager custManager = new CustomerManager(); 
		Customer customer = custManager.getCustomer(customerNumber);		 
		log.info("Processing Shipment for {} ...", customer.getAccountNumber()); 
		 
		OrderManager orderManager = new OrderManager(); 
		Order order = orderManager.getOrder(orderNumber, customerNumber); 
		 
		try {			 
			int i = RandomUtil.getRandomNumberInRange(1, ExceptionListReader.getInstance().size() + 1); 
			String className = (String) ExceptionListReader.getInstance().get(i + ""); 
			Class<Throwable> c = (Class<Throwable>) Class.forName(className); 
			process(customer, order, shipmentDetails, shipNumber, c); 
		} 
		catch (Throwable e) { 
			log.error("Error occured processing order", e); 
		} 
		log.info("Completed processing shipment {}", order.getOrderNumber()); 
	} 
	 
	private <T extends Throwable> void process(Customer customer, Order order,  
			String shipmentDetails, String shipNumber, Class<T> exceptionType) throws Exception, T { 
		 
	    String shippingMessage = "Exception while processing Order " + order.getOrderNumber(); 
	    log.debug("Customer is: {} ShipNumber is {}", customer.getAccountNumber(), shipNumber); 
	    throw exceptionType.getConstructor(String.class).newInstance(shippingMessage); 
	} 
}