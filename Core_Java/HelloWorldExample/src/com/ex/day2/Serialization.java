package com.ex.day2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.ex.pojos.Honda;

public class Serialization {

	static String filename = "src/com/ex/files/bytestream.txt";

	public static void main(String[] args) {

		Honda car = new Honda();
		car.setDescription("Cruizer");
		car.setModel("Tesla");
		car.setMpg(42);

		writeObject(car);

		Honda retrievedCar = (Honda)readObject();
		System.out.println(retrievedCar);

	}

	static void writeObject(Object o) {

		// try-with-resources block
		try(ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(filename))) {

			oos.writeObject(o);

		} catch (IOException e) {
			System.out.println("IOException for filename: " + filename);
		}

	}

	static Object readObject() {
		Object obj = null;
		
		// try-with-resources block
		try(ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(filename))) {

			obj = ois.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return obj;

	}

}