/*
Amazon Interview Question Software Engineer / Developers
http://www.careercup.com/question?id=5743544090230784

Given an array containing only stars '*' and hashes '#'. 
Find longest contiguous sub array that will contain equal number of stars '*' and hashes '#'. 

Order (n) solution required.
*/

import java.util.Random;
import java.util.Arrays;

class LongestContigiousSubArray {
	private static int[] getRandomArray() {
		Random rand = new Random();
		int[] array = new int[20];
		for (int i = 0; i < array.length; i++) {
			array[i] = rand.nextInt(2);
		}
		return array;
	}

	private static void getLongestContigiousSubArray(int[] array) {
		int max = 0, zCount = 0, oCount = 0, prev = -1;
		int indexStartsFrom = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == 0 && prev != 0) {
				zCount = 1;
				prev = 0;
			} else if (array[i] == 0 && prev == 0) {
				zCount++;
			} else if (array[i] == 1 && prev != 1) {
				oCount = 1;
				prev = 1;
			} else if (array[i] == 1 && prev == 1) {
				oCount++;
			}
			if ((max < zCount + oCount) && (zCount == oCount)) {
				max = zCount + oCount;
				indexStartsFrom = i - max + 1;
				// System.out.println("Max is updated to "+ max +" at index "+ i);
			}
		}
		System.out.println("Max: "+ max + " starts from index "+ indexStartsFrom);
	}

	public static void main(String[] args) {
		int[] array;
		for(int i = 0; i < 10; i++) {
			array = getRandomArray();
			System.out.println(Arrays.toString(array));
			getLongestContigiousSubArray(array);
		}
	}
}
