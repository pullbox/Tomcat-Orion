package com.orion.billing;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orion.mockdata.generate.DelayGenerator;
import com.orion.shoppingcart.domain.ShoppingCartProperties;
import com.orion.shoppingcart.exception.ShoppingCartException;
import com.orion.shoppingcart.manager.ShoppingCartPropertyManager;
import com.orion.shoppingcart.workflow.tasks.ShoppingCartTask;
import com.orion.shoppingcart.workflow.tasks.ShoppingCartTaskExecutor;

public class OrionBillingLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(OrionBillingLogin.class);

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.info("Reading Property Variables ...");
		ShoppingCartProperties scProperties = ShoppingCartPropertyManager.populate();
		scProperties.print();

		log.info("Lets start the runs");
		String noOfErrorsStr = request.getParameter("noOfErrors");
		int noOfErrors = 20;
		try {
			noOfErrors = Integer.parseInt(noOfErrorsStr);
		} catch (Exception e) {
			log.error("Defaulting to noOfErrors {}", noOfErrors);
		}

		for (int i=0; i < noOfErrors; i++) {
			Future<?> future = null;
			try {
				ShoppingCartTaskExecutor executor = ShoppingCartTaskExecutor.getInstance(1);
				String threadName = Thread.currentThread().getName() + "-" + i;
				future = executor.submit(new ShoppingCartTask(scProperties, threadName));
				letsWait(scProperties);

			} catch (Exception e) {
				log.error("Exception in executing workflows {}", e.getMessage());
			} finally {
				handleFuture(future);
			}
		}
		
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Orion-Billing</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Orion Billing - Completed Generating " + noOfErrors + " Errors ....</h1>");
        out.println("<br/><br/>");
        out.println("<p><a href=\"../../index.html\">Orion Home</a></p>");
        out.println("</body>");
        out.println("</html>");
	}
	
	private void letsWait(ShoppingCartProperties scProperties) {
		int waitTime = scProperties.getWaitTime();
		if (waitTime != 0) {
			log.info("Waiting for {} seconds between shopping cart thread iterations ....", waitTime/(1000));
			DelayGenerator.introduceDelay(waitTime);
		}
	}
	
	private void handleFuture(Future<?> f) {
		if (f != null) {
			while (true) {
				try {
					//Wait for all threads to complete
					log.info("Waiting for the executor to complete ...");
					f.get();
				} catch (java.util.concurrent.ExecutionException e) {
					log.error("Exception in thread: {}", e);
				} catch (Exception e) {
					log.error("Exception in thread: {}", e);
					throw new ShoppingCartException(e);
				}
				break;
			}
		}
	}
	
}