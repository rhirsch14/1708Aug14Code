package com.revature.dao;



import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.revature.beans.Instructor;
import com.revature.beans.Student;
import com.revature.beans.Transcript;
import com.revature.util.ConnectionUtil;

public class HibernateDAO {
	
	/*
	 * Persists the student to the database
	 * Adds a corresponding transcript for the student as well
	 * Sets the student's id to the generated id and the transcript to the new transcript
	 */
	public void addStudent(Student student) {
		Session session = ConnectionUtil.getSession();
		Transaction transaction = session.beginTransaction();
		
		/*
		 * New empty transcript for the student
		 */
		Transcript transcript = new Transcript();
		int newTranscriptId = (Integer) session.save(transcript);
		transcript.setId(newTranscriptId);
		
		int newStudentId = (Integer) session.save(student);
		student.setId(newStudentId);
		student.setTranscript(transcript);
	
		transaction.commit();
		session.close();
	}
	
	/*
	 * Adds a new instructor to the database
	 * Sets the instructor's id to the generated id 
	 */
	public void addInstructor(Instructor instructor) {
		Session session = ConnectionUtil.getSession();
		Transaction transaction = session.beginTransaction();
		
		int newInstructorId = (Integer) session.save(instructor);
		instructor.setId(newInstructorId);
		try {
			transaction.commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
		}
		session.close();
	}
	
	
	
	public List<Student> getAllStudents() {
		Session session = ConnectionUtil.getSession();
		Criteria criteria = session.createCriteria(Student.class);
		List<Student> students = criteria.list();
		session.close();
		return students;
	}
	
	
	public List<Student> getAllStudentsByCriteria() {
		Session session = ConnectionUtil.getSession();
		Criteria criteria = session.createCriteria(Student.class)
								.add(Restrictions.like("firstName", "first%"));
		List<Student> students = criteria.list();
		session.close();
		return students;
	}
	
	public List<Student> getAllStudentsQuery() {
		Session session = ConnectionUtil.getSession();
		final String hql = "FROM Student";
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		session.close();
		return students;
	}
	
	public List<Student> getAllStudentsByCriteriaQuery() {
		Session session = ConnectionUtil.getSession();
		final String hql = "FROM Student WHERE lower(firstName) like 'first%'";
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		session.close();
		return students;
	}
	
	
	public void deleteStudentByIdCriteria(int id) {
		Session session = ConnectionUtil.getSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(Student.class)
						.add(Restrictions.eq("id", id));
		Student student = (Student) criteria.uniqueResult();
		session.delete(student);
		transaction.commit();
		session.close();
	}
}