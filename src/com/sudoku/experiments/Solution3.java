package com.sudoku.experiments;

public class Solution3 {
	
	public static void main(String[] args) {
		String encodedSudoku = "4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......";
		//String encodedSudoku = "52...6.........7.13...........4..8..6......5...........418.........3..2...87.....";
		//String encodedSudoku = "85...24..72......9..4.........1.7..23.5...9...4...........8..7..17..........36.4.";
		//String encodedSudoku = "..53.....8......2..7..1.5..4....53...1..7...6..32...8..6.5....9..4....3......97..";
		//String encodedSudoku = "..9748...7.........2.1.9.....7...24..64.1.59..98...3.....8.3.2.........6...2759..";
		String solvedSudoku = solve(encodedSudoku);
		System.out.println(solvedSudoku);
	}
	
    // Complete the solve function below.
    static String solve(String grid) {
        char[][] board = getSudokuBoard(grid);
        solveSudoku(board);
        String res = encodeSudokuBoard(board);
        return res;
    }

    static char[][] getSudokuBoard(String encodedSudoku)
    {
        char[][] board = new char[9][9];
        for(int i=0; i<encodedSudoku.length(); i++)
        {
            char boardVal = encodedSudoku.charAt(i);
            board[i/9][i%9] = boardVal;
        }
        return board;
    }
    
    static String encodeSudokuBoard(char[][] board) {
        StringBuffer encodedString = new StringBuffer();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                encodedString.append(board[i][j]);
            }
        }
        return encodedString.toString();
    }

    static void solveSudoku(char[][] board) {
        boolean[][] rows = new boolean[9][9];
        boolean[][] columns = new boolean[9][9];
        boolean[][] squares = new boolean[9][9];

        int emptyCellsCount = buildMaps(board, rows, columns, squares);

        emptyCellsCount = solveSmart(board, rows, columns, squares, emptyCellsCount);

        if (emptyCellsCount > 0) {
            bruteForce(board, rows, columns, squares, 0);
        }
    }

    static int buildMaps(char[][] board, boolean[][] rows, boolean[][] columns, boolean[][] squares) {
        int currentInt;
        int currentChar;
        int emptyCellsCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                currentChar = board[i][j];
                if (currentChar == '.') {
                    emptyCellsCount++;
                    continue;
                }

                currentInt = currentChar - '1';
                rows[i][currentInt] = true;
                columns[j][currentInt] = true;
                squares[3 * (i / 3) + j / 3][currentInt] = true;
            }
        }

        return emptyCellsCount;
    }

    static int solveSmart(char[][] board, boolean[][] rows, boolean[][] columns, boolean[][] squares,
            int emptyCellsCount) {
        boolean found;
        int indexSol = -1;
        int oldEmptyCellsCount = -1;
        while (emptyCellsCount > 0 && emptyCellsCount != oldEmptyCellsCount) {
            oldEmptyCellsCount = emptyCellsCount;
            // Solve by rows
            for (int r = 0; r < 9; r++) { // rows
                for (int n = 0; n < 9; n++) { // numbers
                    if (rows[r][n]) {
                        continue;
                    }
                    found = false;
                    for (int c = 0; c < 9; c++) { // columns
                        if (board[r][c] == '.' && !columns[c][n] && !squares[3 * (r / 3) + c / 3][n]) {
                            if (found) {
                                found = false;
                                break;
                            } else {
                                found = true;
                                indexSol = c;
                            }
                        }
                    }
                    if (found) {
                        rows[r][n] = true;
                        columns[indexSol][n] = true;
                        squares[3 * (r / 3) + indexSol / 3][n] = true;
                        board[r][indexSol] = (char) ('1' + n);
                        emptyCellsCount--;
                    }
                }
            }

            // Solve by columns
            for (int c = 0; c < 9; c++) { // columns
                for (int n = 0; n < 9; n++) { // numbers
                    if (columns[c][n]) {
                        continue;
                    }
                    found = false;
                    for (int r = 0; r < 9; r++) { // rows
                        if (board[r][c] == '.' && !rows[r][n] && !squares[3 * (r / 3) + c / 3][n]) {
                            if (found) {
                                found = false;
                                break;
                            } else {
                                found = true;
                                indexSol = r;
                            }
                        }
                    }
                    if (found) {
                        rows[indexSol][n] = true;
                        columns[c][n] = true;
                        squares[3 * (indexSol / 3) + c / 3][n] = true;
                        board[indexSol][c] = (char) ('1' + n);
                        emptyCellsCount--;
                    }
                }
            }

            // Solve by squares
            int rowInd, columnInd;
            for (int s = 0; s < 9; s++) { // squares
                for (int n = 0; n < 9; n++) { // numbers
                    if (squares[s][n]) {
                        continue;
                    }
                    found = false;
                    for (int p = 0; p < 9; p++) { // square positions
                        rowInd = 3 * (s / 3) + p / 3;
                        columnInd = 3 * (s % 3) + p % 3;
                        if (board[rowInd][columnInd] == '.' && !columns[columnInd][n] && !rows[rowInd][n]) {
                            if (found) {
                                found = false;
                                break;
                            } else {
                                found = true;
                                indexSol = p;
                            }
                        }
                    }
                    if (found) {
                        rowInd = 3 * (s / 3) + indexSol / 3;
                        columnInd = 3 * (s % 3) + indexSol % 3;
                        rows[rowInd][n] = true;
                        columns[columnInd][n] = true;
                        squares[s][n] = true;
                        board[rowInd][columnInd] = (char) ('1' + n);
                        emptyCellsCount--;
                    }
                }
            }
        }

        return emptyCellsCount;
    }

    static boolean bruteForce(char[][] board, boolean[][] rows, boolean[][] columns, boolean[][] squares, int index) {
        if (index == 81) {
            return true;
        }

        int row = index / 9;
        int column = index % 9;
        if (board[row][column] != '.') {
            return bruteForce(board, rows, columns, squares, index + 1);
        }

        for (int n = 0; n < 9; n++) {
            if (!rows[row][n] && !columns[column][n] && !squares[3 * (row / 3) + column / 3][n]) {
                board[row][column] = (char) ('1' + n);
                rows[row][n] = true;
                columns[column][n] = true;
                squares[3 * (row / 3) + column / 3][n] = true;

                if (bruteForce(board, rows, columns, squares, index + 1)) {
                    return true;
                }

                board[row][column] = '.';
                rows[row][n] = false;
                columns[column][n] = false;
                squares[3 * (row / 3) + column / 3][n] = false;
            }
        }

        return false;
    }


}
