package com.san.spring.main;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

public class Billing {

	public static void main(String[] args) {
		
		BeanFactory bean = new XmlBeanFactory(new FileSystemResource("beans.xml"));
		Book book =(Book) bean.getBean("book");
		System.out.println("Book details are :");
		System.out.println("Book Name : "+book.getbName());
		System.out.println("Book Code : "+book.getbCode());
		System.out.println("Book Rate : "+"Rs."+book.getbRate());
	}

}
