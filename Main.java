package com.company;
import java.util.*;
import java.lang.*;
import java.io.*;



public class Main {


    static int visitPattern[][];

    static int result[][];

    static int COUNT;

    static void initD2(int[][] visit,int[][] res){
        visitPattern=visit;
        result=res;
    }
    static boolean isValid(int rows,int cols,int x, int y,
                            int key,
                            int input[][])
    {
        if (x < rows && y < cols &&
                x >= 0 && y >= 0)
        {
            if (visitPattern[x][y] == 0 &&
                    input[x][y] == key)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    static void BFSearch(int rows,int columns,int x, int y, int i,
                         int j, int input[][])
    {
        if (x != y)
            return;

        visitPattern[i][j] = 1;
        COUNT++;

        int xMoves[] = { 0, 0, 1, -1 };
        int yMoves[] = { 1, -1, 0, 0 };


        for (int move = 0; move < 4; move++)
            if ((isValid(rows,columns,i + yMoves[move],
                    j + xMoves[move], x, input)) == true)
                BFSearch(rows,columns,x, y, i + yMoves[move],
                        j + xMoves[move], input);
    }


    static void resetVisitPattern(int rows,int cols)
    {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                visitPattern[i][j] = 0;
    }


    static void saveResult(int rows,int cols,int key,
                           int input[][])
    {
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (visitPattern[i][j] ==1 &&
                        input[i][j] == key)
                    result[i][j] = visitPattern[i][j];
                else
                    result[i][j] = 0;
            }
        }
    }

    // draw the maximum continuous block
    static void print_result(int rows,int cols,int res)
    {
        System.out.println ("largest continuous color block in the given grid is : " +
                res );

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (result[i][j] != 0)
                    System.out.print(result[i][j] + " ");
                else
                    System.out.print("* ");
            }
            System.out.println();
        }
    }

    // Draw the input
    static void print_input(int rows,int cols,int[][]input)
    {
        System.out.println ("The input grid connected : ");

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {

                System.out.print(input[i][j] + " ");

            }
            System.out.println();
        }
    }


    static void computeLargestContinuousColorBlock(int input[][])
    {
        int currentMaxValue = Integer.MIN_VALUE;

        int rows= input.length;
        int columns = input[0].length;

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                // check whether right cell with same value
                resetVisitPattern(rows,columns); // first reset visit pattern to zeros
                COUNT = 0;

                if (j + 1 < columns)
                    BFSearch(rows,columns,input[i][j], input[i][j + 1],
                            i, j, input);

                if (COUNT >= currentMaxValue)
                {
                    currentMaxValue = COUNT;
                    saveResult(rows,columns,input[i][j], input);
                }

                // check whether downward cell with same value
                resetVisitPattern(rows,columns); // first reset visit pattern to zeros
                COUNT = 0;

                if (i + 1 < rows)
                    BFSearch(rows,columns,input[i][j],
                            input[i + 1][j], i, j, input);

                if (COUNT >= currentMaxValue)
                {
                    currentMaxValue = COUNT;
                    saveResult(rows,columns,input[i][j], input);
                }
            }
        }
        print_result(rows,columns,currentMaxValue);
    }

    static int[][] createGrid(int rows,int cols) {

        Random rand = new Random();
        int upperbound = 4;
        //generate random values from 0 to 4

        int[][] arr = new int[rows][cols];

        for (int row = 0; row < arr.length; row++)
        {
            for (int col = 0; col < arr[row].length; col++)
            {   // Assume colours as codes eg: 0,1,2,3
                int int_random = rand.nextInt(upperbound);
                arr[row][col] = int_random;
            }
        }

        return arr;
    }

    public static void main(String[] args)
    {

        int[][] input = createGrid(8,10);
        print_input(8,10,input);
        initD2(new int [input.length][input[0].length],new int [input.length][input[0].length]);

        computeLargestContinuousColorBlock(input);
    }
}
