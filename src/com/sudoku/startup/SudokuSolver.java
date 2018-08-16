package com.sudoku.startup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sudoku.utils.ArrayUtils;
import com.sudoku.utils.SudokuUtils;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class SudokuSolver {

	private int computation = 0;
	private ArrayList<HashSet<Integer>> rowSet = null;
	private ArrayList<HashSet<Integer>> colSet = null;
	private ArrayList<HashSet<Integer>> cubeSet = null;
	SudokuUtils sudokuUtils = null;

	public SudokuSolver() {
		sudokuUtils = new SudokuUtils();
	}

	/**
	 * This function gets the candidates to fill at the position
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private List<Integer> getCandidates(int i, int j) {
		HashSet<Integer> row = rowSet.get(i);
		HashSet<Integer> col = colSet.get(j);
		HashSet<Integer> cube = cubeSet.get(ArrayUtils.cubeMapping(i, j));
		Set<Integer> res = new HashSet<>(row);
		res.retainAll(col);
		res.retainAll(cube);
		List<Integer> result = new ArrayList<>(res);
		return result;
	}

	/**
	 * This function searches over the possible solution space for solving Sudoku
	 * 
	 * @param board
	 * @param vacant
	 * @return
	 */
	private boolean search(int[][] board, List<List<Integer>> vacant) {
		computation++;
		if (vacant.size() == 0) {
			System.out.println("SUDOKU SOLVED");
			return true;
		}

		boolean res = false;
		List<Integer> idx = vacant.remove(vacant.size() - 1);
		int i = idx.get(0);
		int j = idx.get(1);
		List<Integer> candidates = getCandidates(i, j);

		for (Integer c : candidates) {
			board[i][j] = c;
			sudokuUtils.removeValFromSets(i, j, c, rowSet, colSet, cubeSet);
			if (sudokuUtils.isValidSudoku(board)) {
				res = res || search(board, vacant);
				if (res == true)
					break;
			}
			sudokuUtils.addValToSets(i, j, c, rowSet, colSet, cubeSet);
		}
		if (res == false && vacant.size() == 0 && candidates.size() == 0)
			res = true;
		else if (res == false) {
			vacant.add(idx);
			board[i][j] = 0;
		}
		return res;
	}

	/**
	 * This function initializes the data structure for possible keys for Sudoku
	 */
	private void initializeKeySets() {
		if (rowSet == null) {
			rowSet = new ArrayList<>();
		} else {
			rowSet.clear();
		}
		if (colSet == null) {
			colSet = new ArrayList<>();
		} else {
			colSet.clear();
		}
		if (cubeSet == null) {
			cubeSet = new ArrayList<>();
		} else {
			cubeSet.clear();
		}
		computation = 0;
	}

	/**
	 * This is the startup function for solving Sudoku and returns the solved Sudoku String
	 * 
	 * @param encodedSudoku
	 */
	public String solveSudoku(String encodedSudoku) {
		int[][] board = ArrayUtils.decodeSudokuBoard(encodedSudoku);
		initializeKeySets();
		sudokuUtils.populateKeySets(board, rowSet, colSet, cubeSet);
		List<List<Integer>> vacant = ArrayUtils.getVacantPosition(board);
		search(board, vacant);
		String res = ArrayUtils.encodeSudokuBoard(board);
		return res;
	}
	
	/**
	 * This is the startup function for solving Sudoku along with visualizations
	 * 
	 * @param encodedSudoku
	 */
	public void solveSudokuWithVisualization(String encodedSudoku) {
		int[][] board = ArrayUtils.decodeSudokuBoard(encodedSudoku);
		ArrayUtils.printSudokuBoard(board);
		initializeKeySets();
		sudokuUtils.populateKeySets(board, rowSet, colSet, cubeSet);
		List<List<Integer>> vacant = ArrayUtils.getVacantPosition(board);
		System.out.println("SOLVING SUDOKU with missing "+vacant.size()+" entries");
		search(board, vacant);
		ArrayUtils.printSudokuBoard(board);
		System.out.println("SOLVED SUDOKU in " + computation + " computations");
	}
}
