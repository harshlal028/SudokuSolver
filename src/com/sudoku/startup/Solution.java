package com.sudoku.startup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Solution {
	
	public static int[][] getSudokuBoard(String encodedSudoku)
	{
		int[][] board = new int[9][9];
		for(int i=0; i<encodedSudoku.length(); i++)
		{
			char boardVal = encodedSudoku.charAt(i);
			if(boardVal != '.')
				board[i/9][i%9] = Integer.parseInt(boardVal+"");
		}
		return board;
	}
	
	public static void printSudokuBoard(int[][] board)
	{
		for(int i=0; i<9; i++)
		{
			if(i%3 == 0)
				System.out.println("----------------------");
			for(int j=0; j<9; j++)
			{
				if(j%3 == 0)
					System.out.print("|");
				System.out.print(board[i][j] + " ");
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println("----------------------");
	}
	
	public static boolean isValidSudoku(int[][] board) {
		for(int i = 0; i<9; i++)
		{
	        HashSet<Integer> rows = new HashSet<>();
	        HashSet<Integer> columns = new HashSet<Integer>();
	        HashSet<Integer> cube = new HashSet<Integer>();
	        for (int j = 0; j < 9;j++)
	        {
	            if(board[i][j]!=0 && !rows.add(board[i][j]))
	                return false;
	            if(board[j][i]!=0 && !columns.add(board[j][i]))
	                return false;
	            int RowIndex = 3*(i/3);
	            int ColIndex = 3*(i%3);
	            if(board[RowIndex + j/3][ColIndex + j%3]!=0 && !cube.add(board[RowIndex + j/3][ColIndex + j%3]))
	                return false;
	        }
	    }
	    return true;
    }
	
	public static void main(String[] args) {
		String encodedSudoku = "4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......";
		//String encodedSudoku = "52...6.........7.13...........4..8..6......5...........418.........3..2...87.....";
		//String encodedSudoku = "85...24..72......9..4.........1.7..23.5...9...4...........8..7..17..........36.4.";
		//String encodedSudoku = "..53.....8......2..7..1.5..4....53...1..7...6..32...8..6.5....9..4....3......97..";
		
		ArrayList<HashSet<Integer>> rowSet = new ArrayList<>();
		ArrayList<HashSet<Integer>> colSet = new ArrayList<>();
		for(int i=0; i<9; i++)
		{	
			rowSet.add(new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
			colSet.add(new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
		}
		
		HashSet<Integer> obj1 = rowSet.get(0);
		HashSet<Integer> obj2 = colSet.get(0);
		int[][] board = getSudokuBoard(encodedSudoku);
		obj2.remove(1);
		printSudokuBoard(board);
		System.out.println(isValidSudoku(board));
	}
}
