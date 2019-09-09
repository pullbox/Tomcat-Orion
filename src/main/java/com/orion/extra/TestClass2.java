package com.orion.extra;

 
public class TestClass2 { 
 	 
	public void  METHOD_NAME (String customerNumber, String orderNumber, String shipmentDetails, String shipNumber) {	 
		 
		try {			 
			throw new RuntimeException("Error in processing customerNumber: " + customerNumber + " and orderNumber: " + orderNumber);
		} 
		catch (Throwable e) { 
			System.err.println("Error in processing customerNumber: " + customerNumber + " and orderNumber: " + orderNumber);
		}
	} 
}