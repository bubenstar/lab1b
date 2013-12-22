package com.payment.presentation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.payment.application.ApplicationService;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		ApplicationService service = (ApplicationService) context
				.getBean("applicationService");
		// service.addStringToEntityNameStartsWithE();
	}

}
