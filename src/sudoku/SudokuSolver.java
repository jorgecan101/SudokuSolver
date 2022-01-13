package sudoku;

import java.io.*;
import java.util.*;

public class SudokuSolver {

    public static void main(String[] args) {
        // We want to take in a text file with our sudoku board and from that we want to turn it into a 2d array
        // Then we should take the 2d array and check if the board is valid
        try {
            // Read the file we want to solve
            // BufferedReader file = new BufferedReader(new FileReader(args[0])); // We should use command line args tbh
            BufferedReader file = new BufferedReader(new FileReader("C:\\Users\\jorge\\Desktop\\sudoku solver\\test1.txt"));
            // Convert it to a matrix that we can solve
            char[][] board = createBoard(file);
            // first check if our current board is valid or not
            if (!validateBoard(board)) {
                System.out.println("This board cannot be solved");
                return;
            }
            // If we pass the initial check, then now we have to start trying to solve the board
            if (solveBoard(board)) {
                // print out the board with JavaFX or something in the future
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        System.out.print(board[i][j]);
                    }
                    System.out.println();
                }
            }
            else {
                System.out.println("This board could not be solved");
            }
        }
        catch (IOException e) {
            System.out.println("Error:" + e);
        }
    }

    public static char[][] createBoard(BufferedReader file) throws IOException {
        char[][] board = new char[9][9];
        List<Character> flatList = new ArrayList<>(); // for temporary storing our 2d array before we make it
        // read file by line and add to board
        String fileLine = file.readLine();
        while (fileLine != null) {
            for (int i = 0; i < fileLine.length(); i++) {
                char currChar = fileLine.charAt(i);
                if (currChar != ' ' && currChar != '|') {
                    // we add it to our arrayList
                    flatList.add(currChar);
                }
            }
            fileLine = file.readLine();
        }
        int k = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = flatList.get(k++);
            }
        }
        return board;
    }

    public static boolean validateBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            // Use hashsets to check for duplicates
            Set<Character> rows = new HashSet<>();
            Set<Character> cols = new HashSet<>();
            Set<Character> grid = new HashSet<>();
            for (int j = 0; j < board[i].length; j++) {
                // check for valid rows
                if (rows.contains(board[i][j])) {
                    return false;
                }
                // if valid, then we add
                else if (board[i][j] != '.') {
                    rows.add(board[i][j]);
                }
                // check for valid cols
                if (cols.contains(board[j][i])) {
                    return false;
                }
                // if valid, then we add
                else if (board[j][i] != '.') {
                    cols.add(board[j][i]);
                }
                // check for valid 3x3 grid
                int row = 3 * (i / 3);
                int col = 3 * (i % 3);
                if (grid.contains(board[row + j / 3][col + j % 3])) {
                    return false;
                } else if (board[row + j / 3][col + j % 3] != '.') {
                    grid.add(board[row + j / 3][col + j % 3]);
                }
            }
        }
        return true;
    }

    public static boolean solveBoard(char[][] board) {
        // I was thinking of some sort of initial bruteforce approach, where add 1, validate it, then move forward
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '.') {
                    for (int k = 1; k < 10; k++) {
                        board[i][j] = (char)(k+'0');
                        if (validateBoard(board) && solveBoard(board)) {
                            return true;
                        }
                        board[i][j] = '.';
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
