package dev.axolotl.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import dev.axolotl.gameoflife.DialogUI;

/*
**Rules**
Every cell interacts with its eight neighbours, which are the cells that are horizontally,
vertically, or diagonally adjacent. At each step in time, the following transitions occur:

Any live cell with fewer than two live neighbours dies, as if by underpopulation.
Any live cell with two or three live neighbours lives on to the next generation.
Any live cell with more than three live neighbours dies, as if by overpopulation.
Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 */

public class gameoflife
{
    static int game_height = 8;
    static int game_width = 8;
    static String filled_cell_char = "1"; //█
    static String empty_cell_char = "0"; //▯
    // The array storing the state of each cell
    static int[][] gameBoard = new int[game_height][game_width];
    static String gameBoardStr = "";
    public static void pushDialog(String str)
    {
        JOptionPane.showMessageDialog(null, str);
    }

    private static final int[][] NEIGHBOURS = {
            {-1, -1}, {-1, 0}, {-1, +1},
            { 0, -1}, /*cell*/ { 0, +1},
            {+1, -1}, {+1, 0}, {+1, +1}};

    /*
    static int countAliveNeighbours(int x, int y) {
        int count = 0;
        if (gameBoard[x - 1][y - 1] == 1) {
            ++count;
        }
        if (gameBoard[x][y - 1] == 1) {
            ++count;
        }
        if (gameBoard[x + 1][ y - 1] == 1) {
            ++count;
        }
        if (gameBoard[x + 1][ y] == 1) {
            ++count;
        }
        if (gameBoard[x + 1][ y + 1] == 1) {
            ++count;
        }
        if (gameBoard[x][ y + 1] == 1) {
            ++count;
        }
        if (gameBoard[x - 1][y + 1] == 1) {
            ++count;
        }
        if (gameBoard[x - 1][y] == 1) {
            ++count;
        }
        return count;
    }
    */
    static int countAliveNeighbours(int x, int y) {
        int count = 0;
        int rows = gameBoard.length;
        int cols = gameBoard[0].length;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = x + i;
                int newY = y + j;

                // Skip the cell itself
                if (i == 0 && j == 0) {
                    continue;
                }

                // Check if the new coordinates are within the game board
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols) {
                    if (gameBoard[newX][newY] == 1) {
                        ++count;
                    }
                }
            }
        }

        return count;
    }

    public static void updateBoard()
    {
        for (int i = 0; i < gameBoard.length; i++)
        {
            for (int j = 0; j < gameBoard[i].length; j++)
            {
                /*
                i - x
                j - y
                */
                int neighborsAlive = countAliveNeighbours(i,j);
                int cellLife = gameBoard[i][j];
                // Any live cell with fewer than two live neighbours dies, as if by underpopulation.
                if(neighborsAlive < 2)
                {
                    if(cellLife == 1)
                    {
                        // die
                        gameBoard[i][j] = 0;
                        System.out.println("Neighbors less than 2, killing cell " + i + "," + j);
                    }
                }
                // Any live cell with two or three live neighbours lives on to the next generation.
                else if (cellLife == 1 && neighborsAlive == 2 || cellLife == 1 && neighborsAlive == 3)
                {
                    // do nothing
                    System.out.println("Neighbors 2 or 3, keeping cell " + i + "," + j);
                }
                // Any live cell with more than three live neighbours dies, as if by overpopulation.
                else if (cellLife == 1 && neighborsAlive > 3) {
                    //die
                    // die
                    gameBoard[i][j] = 0;
                    System.out.println("Neighbors more than 3, killing cell " + i + "," + j);
                }
                // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
                else if (cellLife == 0 && neighborsAlive == 3) {
                    // revive!
                    gameBoard[i][j] = 1;
                    System.out.println("Neighbors are 3, reviving cell " + i + "," + j);
                }
            }
        }
    }

    public static void updateBoardStr()
    {
        gameBoardStr = "";
        for (int i = 0; i < gameBoard.length; i++)
        {
            gameBoardStr = gameBoardStr + "\n";
            for (int j = 0; j < gameBoard[i].length; j++)
            {
                 //System.out.print(gameBoard[i][j]);
                if(gameBoard[i][j] == 0)
                {
                    gameBoardStr = gameBoardStr + empty_cell_char; //gameBoard[i][j];
                }
                else if(gameBoard[i][j] == 1)
                {
                    gameBoardStr = gameBoardStr + filled_cell_char; //gameBoard[i][j];
                }
            }
        }
    }

    public static void printFormattedArray(int[][] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                sb.append(array[i][j]);
                if (j != array[i].length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) throws InterruptedException {
        //blank out the array
        for (int i = 0; i < gameBoard.length; i++)
        {
            for (int j = 0; j < gameBoard[i].length; j++)
            {
                if (i == 5 && j == 5)
                {
                    gameBoard[i][j] = 1;
                }
                else if (i == 4 && j == 5)
                {
                    gameBoard[i][j] = 1;
                }
                else {
                    System.out.println("Filling blank: " + i + "," + j);
                    gameBoard[i][j] = 1;
                }
            }
        }
        System.out.println("---ARRAY CONTENTS INIT--");
        printFormattedArray(gameBoard);
        System.out.println("------------------------");

        // Try to set cells
        gameBoard[5][5] = 1;
        gameBoard[4][5] = 1;
        gameBoard[6][5] = 1;
        DialogUI dialogUI = new DialogUI();
        while(true)
        {
            // System.out.println(":3");
            updateBoard();

            //System.out.println("-----ARRAY CONTENTS #1--");
            //printFormattedArray(gameBoard);
            //System.out.println("------------------------");

            updateBoardStr();

            //System.out.println("-----ARRAY CONTENTS #2--");
            //printFormattedArray(gameBoard);
            //System.out.println("------------------------");

            //pushDialog(gameBoardStr);
            dialogUI.pushDialog(gameBoard);


            //System.out.println("-----ARRAY CONTENTS #3--");
            //printFormattedArray(gameBoard);
            //System.out.println("------------------------");

            //System.out.println("-----ARRAY CONTENTS-----");
            //printFormattedArray(gameBoard);
            //System.out.println("------------------------");
            Thread.sleep(500);
        }
    }
}
