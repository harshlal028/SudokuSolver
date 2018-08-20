package com.sudoku.experiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution1 {
	
    static ArrayList<HashSet<Integer>> rowSet = new ArrayList<>();
    static ArrayList<HashSet<Integer>> colSet = new ArrayList<>();
    static ArrayList<HashSet<Integer>> cubeSet = new ArrayList<>();

    static int[][] getSudokuBoard(String encodedSudoku)
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

    static void populateKeySets(int[][] board)
    {
        rowSet.clear();
        colSet.clear();
        cubeSet.clear();
        
        for(int i=0; i<9; i++)
        {    rowSet.add(new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
            colSet.add(new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
            cubeSet.add(new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
        }
        
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<9; j++)
            {
                removeValFromSets(i, j, board[i][j]);
            }
        }
    }

    static List<List<Integer>> getVacantPosition(int[][] board)
    {
        List<List<Integer>> vacant = new ArrayList<>();
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<9; j++)
            {
                if(board[i][j] == 0)
                    vacant.add(Arrays.asList(i,j));
            }
        }
        return vacant;
    }

    static boolean isValidSudoku(int[][] board) {
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

    static int cubeMapping(int i, int j)
    {
        if(i<3)
        {
            if(j<3)
                return 0;
            if(j>=3 && j<6)
                return 1;
            if(j>=6)
                return 2;
        }
        if(i>=3 && i<6)
        {
            if(j<3)
                return 3;
            if(j>=3 && j<6)
                return 4;
            if(j>=6)
                return 5;
        }
        if(i>=6)
        {
            if(j<3)
                return 6;
            if(j>=3 && j<6)
                return 7;
            if(j>=6)
                return 8;
        }
        System.out.println("Invalid indexes "+i+","+j);
        return -1;
    }

    static List<Integer> getCandidates(int i, int j)
    {
        HashSet<Integer> row = rowSet.get(i);
        HashSet<Integer> col = colSet.get(j);
        HashSet<Integer> cube = cubeSet.get(cubeMapping(i,j));
        Set<Integer> res = new HashSet<>(row);
        res.retainAll(col);
        res.retainAll(cube);
        List<Integer> result = new ArrayList<>(res);
        return result;
    }
    
    static void addValToSets(int i, int j, Integer val)
    {
        rowSet.get(i).add(val);
        colSet.get(j).add(val);
        cubeSet.get(cubeMapping(i, j)).add(val);
    }
    
    static void removeValFromSets(int i, int j, Integer val)
    {
        rowSet.get(i).remove(val);
        colSet.get(j).remove(val);
        cubeSet.get(cubeMapping(i, j)).remove(val);
    }
    
    static boolean search(int[][]board, List<List<Integer>> vacant)
    {
        if(vacant.size()==0)
        {
            return true;
        }
        
        boolean res = false;
        List<Integer> idx = vacant.remove(vacant.size()-1);
        int i = idx.get(0);
        int j = idx.get(1);
        List<Integer> candidates = getCandidates(i, j);
        
        for(Integer c: candidates)
        {
            board[i][j] = c;
            removeValFromSets(i, j, c);
            if(isValidSudoku(board))
            {
                //printSudokuBoard(board);
                res = res || search(board, vacant);
                if(res == true)
                    break;
            }
            addValToSets(i, j, c);
        }
        if(res==false && vacant.size()==0 && candidates.size()==0)
            res = true;
        else if(res == false)
        {
            vacant.add(idx);
            board[i][j] = 0;
        }
        return res;
    }
    
    static String encodeSudokuBoard(int[][] board) {
        StringBuffer encodedString = new StringBuffer();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                encodedString.append((char) (48 + board[i][j]));
            }
        }
        return encodedString.toString();
    }

    // Complete the solve function below.
    static String solve(String grid) {
        int[][] board = getSudokuBoard(grid);
        populateKeySets(board);
        List<List<Integer>> vacant = getVacantPosition(board);
        search(board, vacant);
        String res = encodeSudokuBoard(board);
        return res;
    }

	public static void main(String[] args) {
		String encodedSudoku = "4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......";
		//String encodedSudoku = "52...6.........7.13...........4..8..6......5...........418.........3..2...87.....";
		//String encodedSudoku = "85...24..72......9..4.........1.7..23.5...9...4...........8..7..17..........36.4.";
		//String encodedSudoku = "..53.....8......2..7..1.5..4....53...1..7...6..32...8..6.5....9..4....3......97..";
		//String encodedSudoku = "..9748...7.........2.1.9.....7...24..64.1.59..98...3.....8.3.2.........6...2759..";
		String solvedSudoku = solve(encodedSudoku);
		System.out.println(solvedSudoku);
	}
}
