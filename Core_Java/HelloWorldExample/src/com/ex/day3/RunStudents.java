package com.ex.day3;

import java.util.ArrayList;
import java.util.Scanner;

import com.ex.pojos.Student;

public class RunStudents {

	static StudentIO sIO = new StudentIO();
	static Scanner scan = new Scanner(System.in);
	
	// Note: this code does not check whether a email is legal
	// or whether the first/last name contains the delimiter
	// used for writing/reading students
	public static void main(String[] args) {

		System.out.println("Welcome to the automated student update system.");
		
		while(true) {
			System.out.println("Enter 1 to add a student, Enter 2 to print a list of all students" +
					"Enter 3 to update a student, \nEnter 4 to delete a student" +
					"Enter 5 to delete all students. Enter q to quit");
			String str = scan.nextLine();

			if (str.equals("1"))
				addStudent();
			else if (str.equals("2"))
				printStudents();
			else if (str.equals("3"))
				updateStudent();
			else if (str.equals("4"))
				deleteStudent();
			else if (str.equals("5")) {

				System.out.println("You are about to DELETE ALL STUDENTS from the system. Are you sure? y/n");
				String str2 = scan.nextLine();

				if (str2.equalsIgnoreCase("y"))
					sIO.deleteAllStudents();
			}
			else if (str.equals("q")) {
				systemExit();
				return;
			}

			System.out.println();
			
		}

	}

	static void addStudent() {
		Student stud = getStudentFromUser();
		
		sIO.addStudent(stud);

	}

	static void printStudents() {

		ArrayList<Student> studList = sIO.getAllStudents();

		if (studList != null)
			for (int i = 0; i < studList.size(); i++)
				System.out.println("Student number " + (i+1) + ": " + studList.get(i).toString());

	}

	static void updateStudent() {
		System.out.println("Enter the email address of student to be updated: ");
		String email = scan.nextLine();
		
		if (sIO.readStudent(email) == null) {
			System.out.println("Student not found");
			return;
		}
		
		System.out.println("Enter student's new information: ");
		Student stud = getStudentFromUser();
		
		if(sIO.updateStudent(email, stud))
			System.out.println("Student updated");
		else System.out.println("Student could not be found");
	}

	static void deleteStudent() {
		System.out.println("Enter the email address of student to be deleted: ");

		if(sIO.deleteStudent(scan.nextLine()))
			System.out.println("Student deleted from the system");
		else System.out.println("Student could not be found");
	}
	
	private static Student getStudentFromUser() {
		Student stud = new Student();
				
		System.out.println("Enter student's first name: ");
		stud.setFirstName(scan.nextLine().trim());
		System.out.println("Enter student's last name: ");
		stud.setLastName(scan.nextLine().trim());
		System.out.println("Enter student's unique email: ");
		stud.setEmail(scan.nextLine().trim());

		return stud;
	}
	
	private static void systemExit() {
		scan.close();
		System.out.println("Thank you for using the automated student update system.");
	}

}