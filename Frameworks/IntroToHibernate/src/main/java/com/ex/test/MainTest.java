package com.ex.test;

import java.time.LocalDate;

import com.ex.beans.Student;
import com.ex.dao.HibernateDao;

public class MainTest {
	
	public static void main(String[] args) {
		
		HibernateDao dao = new HibernateDao();
		Student student = new Student();
		
		student.setFirstName("Zack");
		student.setLastName("Montgomry");
		student.setEmail("zack.attack@hotmail.com");
		student.setBirthDay(LocalDate.now());
		student.setFavoriteColor("Yellow");
		
		dao.addStudent(student);
	}

}