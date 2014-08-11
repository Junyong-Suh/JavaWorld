/*
Google Interview Question Software Engineer / Developers
http://www.careercup.com/question?id=5147801809846272

Given a NxN matrix which contains all distinct 1 to n^2 numbers, 
write code to print sequence of increasing adjacent sequential numbers. 

ex: 
1 5 9 
2 3 8 
4 6 7 

should print 
6 7 8 9
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

class IncreasingMaze {
	enum Direction {
		NORTH, EAST, SOUTH, WEST
	}

	private static int[][] getRandomMatrix(int n) {
		// to shuffle the maze
		ArrayList<Integer> l = new ArrayList<Integer>();
		for (int i = 0; i < n*n; i++) {
			l.add(i);
		}
		Collections.shuffle(l);

		// create the maze
		int[][] maze = new int[n][n];
		int index = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				maze[i][j] = l.get(index++);	
			}		
		}
		return maze;
	}

	private static void printTwoDimensionalArray(int[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
	}

	private static void printSequenceOfIncreasingAdjacentSequentialNumbers(int[][] maze) {
		// look around to see if we can move
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if (canMove(maze, i, j, Direction.NORTH)) {

				} 
				if (canMove(maze, i, j, Direction.EAST)) {

				}
				if (canMove(maze, i, j, Direction.SOUTH)) {

				}
				if (canMove(maze, i, j, Direction.WEST)) {
				
				}
			}
		}			
	}

	private static boolean canMove(int[][] maze, int row, int column, Direction direction) {
		switch (direction) {
			case NORTH:
				break;
			case EAST:
				break;
			case SOUTH:
				break;
			case WEST:
				break;
			default:
				System.err.println("Invalid Direction");
				return false;
		}
		return true;
	}

	public static void main(String args[]) {
		final int SIZE = 4;
		int[][] maze = new int[SIZE][SIZE];
		for (int i = 0; i < 2; i++) {
			maze = getRandomMatrix(SIZE);
			System.out.println("Maze #"+ i);
			printTwoDimensionalArray(maze);
			printSequenceOfIncreasingAdjacentSequentialNumbers(maze);
		}
	}
}
