package com.ex.test;

import java.time.LocalDate;

import com.ex.beans.Course;
import com.ex.beans.Instructor;
import com.ex.beans.Student;
import com.ex.beans.Transcript;
import com.ex.dao.HibernateDao;

public class MainTest {
	
	public static void main(String[] args) {
		
		HibernateDao dao = new HibernateDao();

		Course c = new Course();
		c.setInstructor(dao.getInstructorByID(100));
		c.setDescription("This is a great class!");
		c.setName("Intro to Java 101");
		dao.addCourse(c);
//		Transcript t = st.getTranscript();
//		dao.addCourseToTranscript(t, c);
	}
	
	private static Student createStudent() {
		Student student = new Student();
		
		student.setFirstName("Zack");
		student.setLastName("Montgomry");
		student.setEmail("zack.attack@hotmail.com");
		student.setBirthDay(LocalDate.now());
		student.setFavoriteColor("Yellow");
		
		return student;
	}
	
	private static Instructor createInstructor() {
		Instructor instructor = new Instructor();
		
		instructor.setName("Monty");
		
		return instructor;
	}

}