package com.ex.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ex.beans.Student;

public class Main {

	public static void main(String[] args) throws InterruptedException{

		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

		Student student = (Student) context.getBean("student");

		student.exist();
		student.study();
		student.eat();
		student.work();
		
		student.takeTime();
		student.takeTime();
		student.takeTime();

		((ConfigurableApplicationContext) context).close();
	}

}