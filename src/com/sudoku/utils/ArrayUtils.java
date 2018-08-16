package com.sudoku.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class contains utilities related to encoding and decoding of a Sudoku
 * Puzzle to Arrays
 * 
 * @author Harsh Lal
 *
 */
public class ArrayUtils {

	/**
	 * This function takes an encoded Sudoku String and converts it to an Sudoku
	 * Array
	 * 
	 * @param encodedSudoku
	 * @return
	 */
	public static int[][] decodeSudokuBoard(String encodedSudoku) {
		int[][] board = new int[9][9];
		for (int i = 0; i < encodedSudoku.length(); i++) {
			char boardVal = encodedSudoku.charAt(i);
			if (boardVal != '.')
				board[i / 9][i % 9] = Integer.parseInt(boardVal + "");
		}
		return board;
	}

	/**
	 * This function returns an encoded representation of the Sudoku board
	 * 
	 * @param board
	 * @return
	 */
	public static String encodeSudokuBoard(int[][] board) {
		StringBuffer encodedString = new StringBuffer();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				encodedString.append((char) (48 + board[i][j]));
			}
		}
		return encodedString.toString();
	}

	/**
	 * This function gets the individual cube mappings within the Sudoku
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public static int cubeMapping(int i, int j) {
		if (i < 3) {
			if (j < 3) {return 0;}
			if (j >= 3 && j < 6) {return 1;}
			if (j >= 6) {return 2;}
		}
		if (i >= 3 && i < 6) {
			if (j < 3) {return 3;}
			if (j >= 3 && j < 6) {return 4;}
			if (j >= 6) {return 5;}
		}
		if (i >= 6) {
			if (j < 3) {return 6;}
			if (j >= 3 && j < 6) {return 7;}
			if (j >= 6) {return 8;}
		}
		System.out.println("Invalid indexes " + i + "," + j);
		return -1;
	}

	/**
	 * This function gives the list of vacant position that needs to be filled in
	 * Sudoku
	 * 
	 * @param board
	 * @return
	 */
	public static List<List<Integer>> getVacantPosition(int[][] board) {
		List<List<Integer>> vacant = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] == 0)
					vacant.add(Arrays.asList(i, j));
			}
		}
		return vacant;
	}

	/**
	 * This function prints well formatted Sudoku board
	 * 
	 * @param board
	 */
	public static void printSudokuBoard(int[][] board) {
		for (int i = 0; i < 9; i++) {
			if (i % 3 == 0)
				System.out.println("----------------------");
			for (int j = 0; j < 9; j++) {
				if (j % 3 == 0)
					System.out.print("|");
				System.out.print(board[i][j] + " ");
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println("----------------------");
	}
}
