import java.util.LinkedList;
import java.util.Random;
  
/** @author Haaris Yahya, 7054984, hy20ao
 * @version 1.0 (10/13/2021)  
 * This class contains the functions that are the core of the minimax algorithm where the methods min and max are called 
 * interchangeably until the max depth is reach, in this case 5.
 */

public class MiniMax {
    
    int computersChar; // the variable for which letter the AI receives
    int mxDepth; /**max depth that the tree will reach, we have to declare this variable as the run time can dramatically increase.
                  *Additionally, the depth of the connect4 board is 6 rows. */



    //This method declares the depth of the tree and the connect4 AI's letter and is the constructor
    public MiniMax(int playersChar) {
        mxDepth = 5; 
        computersChar = playersChar;
    }

    //This method calls the max method recursively and launches the minimax algorithm
    public Game followingMove(board board) {

       
        return max(board.expandedBoard(board), 0);
    }

    //this method is the maximizing portion of the minimax algorithm, the AI is the maximizing player, the maximizer plays the 'O'
    public Game max(board board, int depth) { 
        Random rand = new Random();
        // max is called and if the node is terminal or the max depth has been reached, then the heuristic is calculated & returned
        if((board.gameEnd()) || (depth == mxDepth)) {
            Game baseMove = new Game();
            baseMove = baseMove.possibleMove(board.prevMove.row, board.prevMove.col, board.evalFunction());
            return baseMove;
        }else{
            //if the node is not terminal, then the boards are stored in the children
            LinkedList<board> children = new LinkedList<board>(board.child(computersChar));
            Game maxMove = new Game();
            maxMove = maxMove.compareMove(Integer.MIN_VALUE);
            for (int i =0; i < children.size();i++) {
                board child = children.get(i);
                Game move = min(child, depth + 1);
                //the child with the highest value is returned by min
                if(move.getVal() >= maxMove.getVal()) {
                    if ((move.getVal() == maxMove.getVal())) {
                        //random is used incase the heuristic has the same value as the child, we randomly chose either move
                        if (rand.nextInt(2) == 0) {
                            maxMove.setRow(child.prevMove.row);
                            maxMove.setCol(child.prevMove.col);
                            maxMove.setVal(move.getVal());
                        }
                    }
                else {
                    maxMove.setRow(child.prevMove.row);
                    maxMove.setCol(child.prevMove.col);
                    maxMove.setVal(move.getVal());
                    }
                }
            }
            return maxMove;
        }
    }


    //this method is the minimizing portion of the minimax algorithm, the user is the minimizing player, the minimizer plays the 'X'
    public Game min(board board, int depth) {
        Random rand = new Random();  
        // min is called and if the node is terminal or the max depth has been reached, then the heuristic is calculated & returned
        if((board.gameEnd()) || (depth == mxDepth)){
            Game baseMove = new Game();
            baseMove = baseMove.possibleMove(board.prevMove.row, board.prevMove.col, board.evalFunction());
            return baseMove;
        }else{
            //The children-moves of the state are calculated (expansion)
            LinkedList<board> children = new LinkedList<board>(board.child(board.X));
            Game minMove = new Game();
            minMove = minMove.compareMove(Integer.MAX_VALUE);
            for (int i =0; i < children.size();i++) {
                board child = children.get(i);
            
                Game move = max(child, depth + 1);
                //The child with the lowest value is returned by max
                if(move.getVal() <= minMove.getVal()) {
                    if ((move.getVal() == minMove.getVal())) {
                        if (rand.nextInt(2) == 0) { 
                            minMove.setRow(child.prevMove.row);
                            minMove.setCol(child.prevMove.col);
                            minMove.setVal(move.getVal());
                        }
                    }
                    else {
                        minMove.setRow(child.prevMove.row);
                        minMove.setCol(child.prevMove.col);
                        minMove.setVal(move.getVal());
                    }
                }
            }
            return minMove;
        }
    }
    
}