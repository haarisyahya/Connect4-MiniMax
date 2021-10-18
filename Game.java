/** @author Haaris Yahya, 7054984, hy20ao
 * @version 1.0 (10/13/2021)  
 * This class contains the functions that are the core of the minimax algorithm. 
 * 
 * This class ensures that we can update values and retrive values for several variables that are used in the MiniMax and
 * board class.
 */


public class Game {	
    int row;
    int col;
    private int val;      //value of eval function
  
    // this is the constructor
    public Game() {
        row = -1;
        col = -1;
        val = 0;
    }

    //this method gets the value for val
    public int getVal() {
        return val;
    }
    //this setter updates the value of row
    public void setRow(int iRow) {
        row = iRow;
    }
    //this setter updates the value of col
    public void setCol(int iCol) {
        col = iCol;
    }
    //this setter updates the value of val
    public void setVal(int iVal) {
        val = iVal;
    }


    //this method is used to call the eval function in the minimax class
    public Game possibleMove(int row, int col, int val) {
        Game possibleMove = new Game();
        possibleMove.row = row;
        possibleMove.col = col;
        possibleMove.val = val;
        return possibleMove;
    }//end possibleMove

    //method used set the value for the minMove variable and then used to compare with another variable, move.
    public Game compareMove(int val) {
        Game compareMove = new Game();
        compareMove.row = -1;
        compareMove.col = -1;
        compareMove.val = val;
        return compareMove;
    }

    //method that is used to tell us that the move has been made and assign it to prevMov in the board class.
    public Game finishedMove(int row, int col) {
        Game finishedMove = new Game();
        finishedMove.row = row;
        finishedMove.col = col;
        finishedMove.val = -1;
        return finishedMove;
    }

   
     
} 