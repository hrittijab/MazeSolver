
package com.mycompany.mazesolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;


/**
 *This is a maze solver that takes the file with the maze and find the correct 
 * way to travel from the right side, crossing all the required columns and rows
 * to the left side of the maze.
 * @author Hrittija Bhattacharjee
 * ID: 1690737
 * AUSCI 235
 * Date: 26 March,2023
 */
public class MazeSolver { 
    private static final String FILENAME = "C:\\Users\\88019\\Documents"
    + "\\NetBeansProjects\\MazeSolver\\src\\main\\java\\com\\mycompany\\"+      
            "mazesolver\\maze3";
    
    /* This class has 3 variables, the number of rows and columns in the maze,
    and the parent coordinate, the coordinate before the one we are considering.
    */
    private static class Coordinate{
        protected int row;
        protected int col;
        protected Coordinate parent;

        public Coordinate(int row, int col, Coordinate parent) {
            this.row = row;
            this.col = col;
            this.parent= parent;
        }

    }
    static String line;
    static int row = 0;
    static int col;
    static char[][] maze;
    static Stack<Coordinate> stack = new Stack<>();
    


    public static void main(String[] args) {
        File myFile = new File(FILENAME);
        try{
            Scanner input = new Scanner(myFile);
         
            while (input.hasNextLine()){ //to find the number of rows
                line = input.nextLine();
                row++;
                
            }
            col = line.length(); //number of cols
            
            input.close();
            input = new Scanner(myFile);
            maze = new char[row][col];

            for(int i = 0; i < row; i++){
                line = input.nextLine();
                for (int j = 0; j < col; j++){
                    maze[i][j] = line.charAt(j); 
                    //for all the characters in the maze to be printed exactly
                    //that way in the 2D array as the actual maze.
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("ERROR - file not found");
        }
        for(int i = 0; i < row; i++){
            if (maze[i][0] == ' ') { //for the starting position, coordinate
                stack.push(new Coordinate(i,0,null));
            }
        }
        findPath();
        
  
    }
    /**
     * This method prints the maze with the rows and columns
     */
    public static void printMaze(){
        for (int i = 0; i < row; i++){
                    for (int j = 0; j < col; j++){
                        System.out.print(maze[i][j]);
                    }
                    System.out.println();
                }
    }
    /**
     * This method takes the present coordinate and pushes all its adjacent 
     * coordinates into the stack which have empty spaces, to move forward in 
     * the maze
     * @param currentCord the row and the column, the position is currently at.
     */
    public static void adjacentCord(Coordinate currentCord){
        //down direction
        /*All the if statements check if the adjacent coordinates are in the 
          rows and cols range and are empty spaces. It also saves the current 
          coordinate as the parent coordinate for later use of tracing it back.
        */
        if (currentCord.row+1 < row && 
                maze[currentCord.row+1][currentCord.col] == ' '){
            stack.push(new Coordinate
                (currentCord.row+1,currentCord.col,currentCord));
        }
        //left direction
        if (currentCord.col-1 >= 0 &&
                maze[currentCord.row][currentCord.col-1] == ' '){
            stack.push(new Coordinate 
            (currentCord.row,currentCord.col-1,currentCord));
        }
        //right direction
        if (currentCord.col+1 < col  &&
                maze[currentCord.row][currentCord.col+1] == ' '){
            stack.push(new Coordinate 
            (currentCord.row,currentCord.col+1,currentCord));
        }
        //up direction
        if (currentCord.row-1 >= 0 && maze[currentCord.row-1]
                [currentCord.col] == ' '){
            stack.push(new Coordinate
            (currentCord.row-1,currentCord.col,currentCord));
        }
    }
    
    /**
     * This method pops the current coordinate and prints a dot on all the 
     * possible positions to find the path.
     */
    public static void findPath(){
        while( !stack.isEmpty()){
            Coordinate currentCord = stack.pop();
            maze[currentCord.row][currentCord.col] = '.'; 
            //this puts dots in all the possible spaces of travel
            if (currentCord.col == col-1){
                makePath(currentCord);
                printMaze();
                
                return;
                
            }
            adjacentCord(currentCord);
            
        }
        System.out.println("NO SOLUTION");
    }
    
    /**
     * This method first changes all the dots to empty spaces again to make the
     * final path with the dots that actually leads to the end of the maze. It
     * then prints the dot from the end to the start backwards, using the parent
     * coordinates.
     * @param currentCord the current position in row and column.
     */
    public static void makePath(Coordinate currentCord){
        for(int i = 0; i < row; i++){
            for (int j = 0;j < col; j++){
                if(maze[i][j] == '.'){
                    maze[i][j] = ' '; //changing all the dots to empty spaces 
                                      //again so that the main path can be drawn
                            
                }
            }
        }
        //only mark the parent coordinate starting from the last
        while (currentCord != null){
            maze[currentCord.row][currentCord.col] = '.';
            currentCord = currentCord.parent;
        }
    }
    
}

        
