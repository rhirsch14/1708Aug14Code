package com.ex.day3;

import java.util.ArrayList;
import java.util.Scanner;

import com.ex.pojos.Student;
//import com.ex.pojos;

public class RunStudents {
	static StudentIO io;

	public static void main(String[] args) {
		//addStudent();
		getStudents();
		
	}
		static void addStudent(){
			io = new StudentIO();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter first name:");
		String fn = scan.nextLine().trim();
		System.out.println("enter last name:");
		String ln = scan.nextLine().trim();
		System.out.println("enter email:");
		String email = scan.nextLine().trim();
		
		Student student = new Student(fn,ln,email);
		
		io.writeStudents(student);
		}
		static void getStudents(){
			io = new StudentIO();
			ArrayList<Student> students = io.readStudents();
			for(Student s: students){
				System.out.println(s.toString());
			}
			
		}
			
				
		

	}

