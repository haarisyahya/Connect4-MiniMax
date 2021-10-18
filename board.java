import java.util.LinkedList;
import java.util.Scanner;

/**@author Haaris Yahya, 7054984, hy20ao
 * @version 1.0 (10/13/2021)          
 * Here is the definition of the board for Connect4 with a main method that contains cases to run the Connect4 game with MiniMax
 */

public class board {

    static int colPos;
    static board theBoard;
    static MiniMax AI; //the minimax function
    static final int X = 1;     //value for the user
    static final int O = -1;    //value for the AI

    int [][] connect4Board; //initializing the 2D array
    Game prevMove; //We use the class Game to set a variable prevMove that helps us know who made the last move, AI or User
    int prevPlayedLetter; //variable that helps us set who makes the first move
    int winner; 
    String winningWay;   //String that outputs how the user won the game
   
   

    //the connect4 board
    public board() {
        prevMove = new Game();
        prevPlayedLetter = O; //This ensures that the User starts first, can be interchanged with X to make the AI start first
        winner = 0; 
        connect4Board = new int[6][7]; //initializing the 2D array, a 6X7 array, the connect4 board
        for(int i=0; i<6; i++) {
            for(int j=0; j<7; j++) 
                connect4Board[i][j] = 0; // set it to 0
            }
        }

     //Printing the connect4 game, X is for the user, O for the AI
     public void printBoard() {
        
        System.out.println();
        for (int i=0; i<6; i++) {
            for (int j=0; j<7; j++) {
                    if (connect4Board[i][j] == 1) {
                        System.out.print("| " + "X" + " "); 
                    } else if (connect4Board[i][j] == -1) {
                        System.out.print("| " + "O" + " "); 
                    } else {
                        System.out.print("| " + " " + " ");
                        
                    }
            }
            System.out.println("|"); 
            
        }
        System.out.println("  1   2   3   4   5   6   7");
    }


     //this setter updates the variable winner
     public void setWinner(int winner) {
        this.winner = winner;
    }

    //this setter updates the string " "
    public void setWinnerMethod(String winningWay) {
        this.winningWay = winningWay;
    }//end setWinnerMethod


     //this method searches for an empty spot in the board to place the 'X' or 'O'
     public int rowLocation(int col) {
        int rowPosition = -1;
        for (int row=0; row<6; row++) {
            if (connect4Board[row][col] == 0) {
                    rowPosition = row;
            }
        }
        return rowPosition;
    }
    
   
    //this method uses the Game class to make a move based on the past moves 
    public void makeMove(int c, int letter) {
        prevMove = prevMove.finishedMove(rowLocation(c), c);
        connect4Board[rowLocation(c)][c] = letter;
        prevPlayedLetter = letter;
    }
	
    //this method checks whether a move is allowed or if the space is empty
    public boolean allowedMove(int col) {
        int row = rowLocation(col);
        if ((row == -1) || (col == -1) || (row > 5) || (col > 6)) {
            return false;
        }
        if(connect4Board[row][col] != 0) {
            return false;
        }
        return true;
    }
	
    //another boolean method that checks whether it is possible to make the move or not (within bounds)
    public boolean canMove(int row, int col) {
        //We evaluate mainly the limits of the board 
        if ((row <= -1) || (col <= -1) || (row > 5) || (col > 6)) {
            return false;
        }
        return true;
    }
	
    //this method checks whether a column is already full or not and if it is, then it prints  message telling us to pick another one
    public boolean fullColumn(int col) {
        if (connect4Board[0][col] == 0)
            return false;
        else{
            System.out.println("The column "+(col+1)+" is full. Select another column.");
            return true;
        }
    }
	
   
      //This method helps us make several boards that can be stored in our data structure (linked list)
      public board expandedBoard(board board) {
        board expansion = new board();
        expansion.prevMove = board.prevMove;
        expansion.prevPlayedLetter = board.prevPlayedLetter;
        expansion.winner = board.winner;
        expansion.connect4Board = new int[6][7];
        for(int i=0; i<6; i++) {
            for(int j=0; j<7; j++) {
                expansion.connect4Board[i][j] = board.connect4Board[i][j];
            }
        }
        return expansion;
    }
	
    //This is the method that initializes the linked list that holds the children or the different states of the board
    public LinkedList<board> child(int letter) {
        LinkedList<board> children = new LinkedList<board>();
        for(int col=0; col<7; col++) {
            if(allowedMove(col)) {
                board child = expandedBoard(this);
                child.makeMove(col, letter);
                children.add(child);
            }
        }
        return children;
    }
	
    /**This is the method houses the evalFunction, assigning points if the AI or User wins. 
     * +100 if the AI wins and -100 if the user wins (since we are the minimizer)
     * +50 & -50 if there are 3 "X"'s or "O"'s in a row respectively
     * +25 & -25 if there are 1 "X"'s or "O"'s in a row respectively
     */
    public int evalFunction() {
        int XL = 0;
        int OL = 0;
        if (winCondition()) {
            if(winner == X) {
                XL = XL + 100;
            } else {
                OL = OL + 100;
            }
        }	
        XL  = XL + threePieces(X)*50 + twoPieces(X)*25;
        OL  = OL + threePieces(O)*50 + twoPieces(O)*25;
	return OL - XL;
    }
	/** This method basically checks are the different ways that a user or AI chas 2 pieces continously (by row, column, 
     * ascendent diag or descendant diag) */
    public int twoPieces(int player) {	
        int count = 0;
        
        //2 in a column
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 1, j)) {
                    if (connect4Board[i][j] == player &&
                        connect4Board[i][j] == connect4Board[i - 1][j]) {
                        count++;
                    }
                }
            }
        }

        //2 in a ascendant diagonal 
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i + 1, j + 1)) {
                    if (connect4Board[i][j] == player && 
                        connect4Board[i][j] == connect4Board[i + 1][j + 1]) {
                        count++;
                    }
                }
            }
        }

        //2 in a row
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i, j + 1)) {
                    if (connect4Board[i][j] == player && 
                        connect4Board[i][j] == connect4Board[i][j + 1]) {
                        count++;
                    }
                }
            }
        }

        //2 in a descendant diagonal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 1, j + 1)) {
                    if (connect4Board[i][j] == player &&
                        connect4Board[i][j] == connect4Board[i - 1][j + 1]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    //this method is responsible for checking there is a winner that fulfills the win conditions
    public boolean gameEnd() {
        
        if (winCondition()) {
            return true;
        }
        //this loops checks to see if there's empty spaces on the board
        for(int r=0; r<6; r++) {
            for(int c=0; c<7; c++) {
                if(connect4Board[r][c] == 0) {
                    return false;
                }
            }
        }
        return true;
    }


    /** This method basically checks are the different ways that a user or AI chas 3 pieces continously (by row, column, 
     * ascendent diag or descendant diag) */
    public int threePieces(int player) {	
        int count = 0;
        

        //3 in a column
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 2, j)) {
                    if (connect4Board[i][j] == player &&
                        connect4Board[i][j] == connect4Board[i - 1][j] && 
                        connect4Board[i][j] == connect4Board[i - 2][j]) {
                        count++;
                    }
                }
            }
        }

        //3 in a ascendent diagonal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i + 2, j + 2)) {
                    if (connect4Board[i][j] == player &&
                        connect4Board[i][j] == connect4Board[i + 1][j + 1] && 
                        connect4Board[i][j] == connect4Board[i + 2][j + 2]) {
                        count++;
                    }
                }
            }
        }

        //3 in a row
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i, j + 2)) {
                    if (connect4Board[i][j] == player && 
                        connect4Board[i][j] == connect4Board[i][j + 1] && 
                        connect4Board[i][j] == connect4Board[i][j + 2]) {
                        count++;
                    }
                }
            }
        }

        //3 in a descendent diagonal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 2, j + 2)) {
                    if (connect4Board[i][j] == player &&
                        connect4Board[i][j] == connect4Board[i - 1][j + 1] && 
                        connect4Board[i][j] == connect4Board[i - 2][j + 2]) {
                        count++;
                    }
                }
            }
        }
        return count;				
    }


    //This method basically checks are the different ways that a user or AI can win (by row, column, ascendent diag or descendant diag)
    public boolean winCondition() {
    
    // when we have a connect4 in a column
        for (int i=5; i>=3; i--) {
            for (int j=0; j<7; j++) {
                if (connect4Board[i][j] != 0 && 
                    connect4Board[i][j] == connect4Board[i-1][j] && 
                    connect4Board[i][j] == connect4Board[i-2][j] && 
                    connect4Board[i][j] == connect4Board[i-3][j]) {
                    setWinner(connect4Board[i][j]);
                    setWinnerMethod("Winner by row!");
                    return true;
                }
            }
        }
		
    // when we have connect4 via an ascendant diagonal
        for (int i=0; i<3; i++) {
            for (int j=0; j<4; j++) {
                if (connect4Board[i][j] != 0 && 
                    connect4Board[i][j] == connect4Board[i+1][j+1] && 
                    connect4Board[i][j] == connect4Board[i+2][j+2] && 
                    connect4Board[i][j] == connect4Board[i+3][j+3]) {
                    setWinner(connect4Board[i][j]);
                    setWinnerMethod("Winner by ascendant diagonal!");
                    return true;
                }
            }
        }

	// when we have connect4 in a row
    for (int i=5; i>=0; i--) {
        for (int j=0; j<4; j++) {
            if (connect4Board[i][j] != 0 && 
                connect4Board[i][j] == connect4Board[i][j+1] && 
                connect4Board[i][j] == connect4Board[i][j+2] && 
                connect4Board[i][j] == connect4Board[i][j+3]) {
                setWinner(connect4Board[i][j]);
                setWinnerMethod("Winner by row!");
                return true;
            }
        }
    }
    
    // when we have connect4 via a descendant diagonal
        for (int i=0; i<6; i++) {
            for (int j=0; j<7; j++) {
                if (canMove(i-3,j+3)) {
                    if (connect4Board[i][j] != 0 &&
                        connect4Board[i][j] == connect4Board[i-1][j+1] && 
                        connect4Board[i][j] == connect4Board[i-2][j+2] && 
                        connect4Board[i][j] == connect4Board[i-3][j+3]) {
                        setWinner(connect4Board[i][j]);
                        setWinnerMethod("Winner by descendant diagonal!");
                        return true;
                    }
                }
            }
        }
        //In case there is not winner after the check is done
        setWinner(0);
        return false;
    }
	
    

    
   
    /*Finally, this is the main method that contains the cases to run the connect4 game, this method contains the code to get input
    * from the user and the minimax algorithm */
    public static void main(String[] args) {	
    
	AI = new MiniMax(board.O); //initializing the AI & board
	theBoard = new board();
	System.out.println("Connect 4! \n");
	theBoard.printBoard();
    
	while(!theBoard.gameEnd()) { //as the game is not over
            System.out.println();
            switch (theBoard.prevPlayedLetter) { //this sets up the order, if X played last then O plays next and vice versa
            
                case board.O:
                    System.out.print("User 'X' makes a move.");
                    try {
                        do {
                            System.out.print("\nPlease select a column to drop your piece (1-7): ");
                            Scanner input = new Scanner(System.in);
                            colPos = input.nextInt();

                        //if the column is not full, catch an exception and print a message if any number outside of 1-7 is inputted
                        } while (theBoard.fullColumn(colPos-1)); 
                    } catch (Exception e){
                        System.out.println("\nValid numbers are 1-7. Please try again!\n");
                        break;
                    }
                    //this is for the movement of the user
                    theBoard.makeMove(colPos-1, board.X); 
                    System.out.println();
                    break;
            
                case board.X:
                    Game computerMove = AI.followingMove(theBoard);
                    theBoard.makeMove(computerMove.col, board.O);
                    System.out.println("Jackmerius (AI) 'O' inserted in column "+(computerMove.col+1)+".");
                    System.out.println();
                    break;
                default:
                    break;
            }
            theBoard.printBoard();
        }
        //these are the messages that display when the game is over, either if the AI won or the user
        System.out.println();
        if (theBoard.winner == board.X) {
            System.out.println("User 'X' is victorious! :)");
            
            System.out.println(theBoard.winningWay);
            System.out.println("The game has come to a conclusion!");
        } else if (theBoard.winner == board.O) {
            System.out.println("Defeat! Jackmerius (AI) 'O' wins! :(");
        
            System.out.println(theBoard.winningWay);
            System.out.println("The game has come to a conclusion!");
        } else {
            System.out.println("Well fought, but alas, it is a tie!");
            System.out.println("The game has come to a conclusion! If you are not satisfied, please play again! :D");
        }
      
        
    }
}