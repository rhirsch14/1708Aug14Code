package com.example.fileio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.example.pojos.Honda;

public class Serialization {
	
	static final String FILE = "src/com/example/fileio/bytestream.txt";

	public static void main(String[] args) {
		Honda honda = new Honda("A honda.", 100, "Accord");
		
		writeObject(honda);
		
		honda = (Honda)readObject();
		
		System.out.println(honda);
	}
	
	static void writeObject(Object obj) {
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE));) {
			oos.writeObject(obj);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static Object readObject() {
		Object obj = null;
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
			obj = ois.readObject();
		}
		catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
}