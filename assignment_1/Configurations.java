/*
 * Rosaline Scully
 * Student ID: 250966670
 * July 22, 2024
 * 
 * This class has different methods that revolve around the current
 * configuration of the board.
 */
public class Configurations {
    private final char[][] board;
    private final int boardSize;
    private final int winningLength;
    private final int maxLevels;

    //constructor
    public Configurations(int boardSize, int lengthToWin, int maxLevels) {
        this.boardSize = boardSize;
        this.winningLength = lengthToWin;
        this.maxLevels = maxLevels;
        this.board = new char[boardSize][boardSize];

        // Initialize the board with spaces
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = ' ';
            }
        }
    }

    //creates a hash dictionary
    public HashDictionary createDictionary(){
        return new HashDictionary(9973);
    }

    /*
     * checks if a configuration exists in the hash table already
     * retrieves it's score. if it doesn't exist, return -1
     */
    public int repeatedConfiguration(HashDictionary hashTable){
        StringBuilder currBoard = new StringBuilder(boardSize*boardSize);

        for(int i = 0; i < boardSize; i++){
            for(int j=0; j < boardSize; j++){
                currBoard.append(board[i][j]);
            }
        }

        String config = currBoard.toString();

        if(hashTable.get(config) == -1) return -1;

        return hashTable.get(config);

    }

    //adds the configuration of the current board to a hash dictionary
    public void addConfiguration(HashDictionary hashDictionary, int score){
        StringBuilder currBoard = new StringBuilder(boardSize*boardSize);

        for(int i = 0; i < boardSize; i++){
            for(int j=0; j < boardSize; j++){ //use stringbuilder to create the string and append it
                currBoard.append(board[i][j]);
            }
        }

        String config = currBoard.toString();

        Data element = new Data(config,score);

        hashDictionary.put(element);
    }

    //saves a character to the board in a specified row and column
    public void savePlay(int row, int col, char symbol){
        board[row][col] = symbol;
    }
    
    //checks if a certain row and column on the board is empty
    public boolean squareIsEmpty(int row, int col){
        return board[row][col] == ' ';
    }
    
    //checks if there are any wins currently on the board
    public boolean wins(char symbol) {
        // check rows
        for (int i = 0; i < boardSize; i++) {
            if (checkLine(symbol, board[i])) {
                return true;
            }
        }

        // check columns
        for (int j = 0; j < boardSize; j++) {
            char[] column = new char[boardSize];
            for (int i = 0; i < boardSize; i++) {
                column[i] = board[i][j];
            }
            if (checkLine(symbol, column)) {
                return true;
            }
        }

        // check diagonals
        for (int i = 0; i <= boardSize - winningLength; i++) {
            for (int j = 0; j <= boardSize - winningLength; j++) {
                if (checkDiagonal(symbol, i, j, 1, 1) || checkDiagonal(symbol, i, boardSize - 1 - j, 1, -1)) {
                    return true;
                }
            }
        }

        return false;
    }
    
    //checks if there are any winners. checks if there are any empty squares left.
    public boolean isDraw(){
        if(wins('O') || wins('X')) return false;
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(squareIsEmpty(i,j)) return false;
            }
        }

        return true;
    }
    
    //evaluates the current board and determines if there are any winners, if it's a draw, or undecided
    public int evalBoard(){
        //check if computer has won
        if(wins('O')) return 3;

        //check if human has won
        if (wins('X')) return 0;

        //check if there are empty positions meaning game is undecided
        //if true, return 1. if false, return 2
        if(isDraw()) {
            return 1;
        } else{
            return 2;
        }

    }

    //checks if there are any wins within a line when given a symbol
    private boolean checkLine(char symbol, char[] line) {
        int count = 0;
        for (char c : line) { //loops through each c in a line
            if (c == symbol) {
                count++;
                if (count >= winningLength) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }
    
    //checks if there is a win within a diagonal line
    private boolean checkDiagonal(char symbol, int startRow, int startCol, int rowIncrement, int colIncrement) {
        int count = 0;
        for (int i = 0; i < winningLength; i++) {
            int row = startRow + i * rowIncrement;
            int col = startCol + i * colIncrement;
            if (board[row][col] == symbol) {
                count++;
                if (count >= winningLength) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }


}
