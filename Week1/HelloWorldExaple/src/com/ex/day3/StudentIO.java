package com.ex.day3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import com.ex.pojos.Student;

public class StudentIO {
	static String filename = "src/com/ex/files/students.txt";
	
	public void writeStudents(Student s){		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));){
			String text = "";
			
			text = text.concat(s.getFirstname()+":");
			text = text.concat(s.getLastname()+":");
			text = text.concat(s.getEmail()+"\n");
			
			bw.write(text);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
	public ArrayList<Student> readStudents(){
		
		ArrayList<Student> list = new ArrayList<Student>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(filename));){
			
			String line = null;
			while((line=br.readLine()) != null){
				Student temp = new Student();
				String[] states = line.split(":");
				for(String x : states){
					System.out.println(x);
				}
				temp.setFirstname(states[0]);
				temp.setLastname(states[1]);
				temp.setEmail(states[2]);
				list.add(temp);
				}
			} 
		catch(FileNotFoundException e){
			e.printStackTrace();
			}
		catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
		
		//return null;
		
	}
	

