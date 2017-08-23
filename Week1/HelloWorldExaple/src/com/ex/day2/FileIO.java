package com.ex.day2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
public class FileIO {
	static String filename = "src/com/ex/files/log.txt";
	static Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	public static void main(String[] args) {
		writeMessage("Hello Jathmel");


	}
	static void writeMessage(String message){
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));){
			bw.write(timestamp + " " + message + "\n");
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}
//	static ArrayList<String> readMessage(){
//		ArrayList messages = new ArrayList<String>();
//		try(BufferedReader br = new BufferedReader(new FileReader(filename));){
//			String line = null;
//			
//				while ((line=br.readLine()) != null){
//					messages.add(line);
//					System.out.println(line);
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
	}
