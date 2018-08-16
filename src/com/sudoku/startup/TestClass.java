package com.sudoku.startup;

import java.util.Arrays;
import java.util.List;

public class TestClass {
	public static void main(String[] args) {
		SudokuSolver sudokuSolver = new SudokuSolver();
		List<String> sudokuPuzzles = Arrays.asList(
				"..9748...7.........2.1.9.....7...24..64.1.59..98...3.....8.3.2.........6...2759..",
				"..53.....8......2..7..1.5..4....53...1..7...6..32...8..6.5....9..4....3......97..",
				"85...24..72......9..4.........1.7..23.5...9...4...........8..7..17..........36.4.",
				"52...6.........7.13...........4..8..6......5...........418.........3..2...87.....",
				"4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......");
		
		for (String encodedSudoku : sudokuPuzzles) {
			System.out.println("\n ===== Cranking the Machine ===== \n");
			sudokuSolver.solveSudokuWithVisualization(encodedSudoku);
			System.out.println("\n ===== Viola we have a Match ===== \n");
		}

		for (String encodedSudoku : sudokuPuzzles) {
			System.out.println("\n ===== Cranking the Machine ===== \n");
			String solved = sudokuSolver.solveSudoku(encodedSudoku);
			System.out.println(encodedSudoku);
			System.out.println(solved);
			System.out.println("\n ===== Viola we have a Match ===== \n");
		}

	}

}
