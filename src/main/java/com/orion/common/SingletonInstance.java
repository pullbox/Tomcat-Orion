package com.orion.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orion.mockdata.generate.utils.RandomUtil;
import com.orion.shoppingcart.exception.ShoppingCartException;

public class SingletonInstance {

	private static final Logger log = 
		LoggerFactory.getLogger(SingletonInstance.class);

	private static SingletonInstance instance = null;
	
	//Private Constructor to avoid initiation
	private SingletonInstance() {
    }

	public static synchronized SingletonInstance getInstance() {
		if (instance == null) {
			instance = new SingletonInstance();
		}
		return instance;
	}

	public void generateRandomExceptions() {
		log.info("Inside Generate Random Exceptions ....");
		int i = RandomUtil.getRandomNumberInRange(1, 11);
		if (i == 5) {
			log.error("Singleton Object: Lets throw an exception");
			throw new ShoppingCartException();
		}
	}
}