package com.hw.problem1;

import java.util.Arrays;

public class BubbleSort {

	public static void main(String[] args) {
		
		int[] array = new int[] {1, 0, 5, 6, 3, 2, 3, 7, 9, 8, 4};			
		System.out.println("This is the unsorted array: " + Arrays.toString(array));
		
		bubbleSort(array); // this call the static bubbleSort method to sort the array
		
		System.out.println("This is the sorted array: " + Arrays.toString(array));

	}
	
	// this method does the sorting.
	static void bubbleSort(int[] array){
		int temp =0;
		for(int i = 0; i < array.length; ++i){
			for(int j = 1; j < array.length-i; j++){
				if(array[j] < array[j-1]){
					temp = array[j-1];
					array[j-1] = array[j];
					array[j] = temp;					
				}
			}
		}
	}

}