package com.sudoku.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * This class contains utilities specific to solving a Sudoku puzzle
 * 
 * @author Harsh Lal
 *
 */
public class SudokuUtils {

	/**
	 * This function populates possible allowed values for rows, columns and cubes
	 * 
	 * @param board
	 * @param rowSet
	 * @param colSet
	 * @param cubeSet
	 */
	public void populateKeySets(int[][] board, ArrayList<HashSet<Integer>> rowSet, ArrayList<HashSet<Integer>> colSet,
			ArrayList<HashSet<Integer>> cubeSet) {
		for (int i = 0; i < 9; i++) {
			rowSet.add(new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
			colSet.add(new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
			cubeSet.add(new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				removeValFromSets(i, j, board[i][j], rowSet, colSet, cubeSet);
			}
		}
	}

	/**
	 * This function adds possible values for a row, column and cube
	 * 
	 * @param i
	 * @param j
	 * @param val
	 * @param rowSet
	 * @param colSet
	 * @param cubeSet
	 */
	public void addValToSets(int i, int j, Integer val, ArrayList<HashSet<Integer>> rowSet,
			ArrayList<HashSet<Integer>> colSet, ArrayList<HashSet<Integer>> cubeSet) {
		rowSet.get(i).add(val);
		colSet.get(j).add(val);
		cubeSet.get(ArrayUtils.cubeMapping(i, j)).add(val);
	}

	/**
	 * This function removes possible values for a row, column and cube
	 * 
	 * @param i
	 * @param j
	 * @param val
	 * @param rowSet
	 * @param colSet
	 * @param cubeSet
	 */
	public void removeValFromSets(int i, int j, Integer val, ArrayList<HashSet<Integer>> rowSet,
			ArrayList<HashSet<Integer>> colSet, ArrayList<HashSet<Integer>> cubeSet) {
		rowSet.get(i).remove(val);
		colSet.get(j).remove(val);
		cubeSet.get(ArrayUtils.cubeMapping(i, j)).remove(val);
	}

	/**
	 * This function checks if the given Sudoku board is valid or not
	 * 
	 * @param board
	 * @return
	 */
	public boolean isValidSudoku(int[][] board) {
		for (int i = 0; i < 9; i++) {
			HashSet<Integer> rows = new HashSet<>();
			HashSet<Integer> columns = new HashSet<Integer>();
			HashSet<Integer> cube = new HashSet<Integer>();
			for (int j = 0; j < 9; j++) {
				if (board[i][j] != 0 && !rows.add(board[i][j]))
					return false;
				if (board[j][i] != 0 && !columns.add(board[j][i]))
					return false;
				int RowIndex = 3 * (i / 3);
				int ColIndex = 3 * (i % 3);
				if (board[RowIndex + j / 3][ColIndex + j % 3] != 0
						&& !cube.add(board[RowIndex + j / 3][ColIndex + j % 3]))
					return false;
			}
		}
		return true;
	}
}
