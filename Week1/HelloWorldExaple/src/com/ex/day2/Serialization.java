package com.ex.day2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystemNotFoundException;

public class Serialization {
	static String file = "src/com/ex/files/Bytestream.txt";


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	static void writeObject(Object o){
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
			oos.writeObject(o);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();}
	}
//	catch (FileSystemNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}

}