package com.example.sudokusolver;


public class Java {
    public static final int Grind_size=9;
    public static final String args1= "1'3'6'2'4'1'2'5'7'3'1'3'3'3'4'3'8'8'4'2'5'5'4'6'5'8'4'6'2'9'6'5'2'6'9'5'7'7'9'7'9'7'8'3'9'8'4'2'8'6'3'9'9'1";

    public static void main(String[] args){



        int[][] board={
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        String[] strArray= args1.split("'");
        System.out.println(strArray[0]);
        sudokuboard(board, strArray);

        System.out.println("Solved successfully!");
        if(solveSudoku(board)){
            System.out.println("Solved successfully!");
        }else{
            System.out.println("Unsolvable board :(");
        }

        printBoard(board);
    }

    private static void printBoard(int[][] board) {
        for(int row=0; row<Grind_size; row++){
            for(int column=0; column<Grind_size; column++){
                System.out.print( board[row][column]);
            }
            System.out.println();
        }
    }

    private static boolean isNumberinRow(int[][] board, int number, int row){
        for(int i=0; i<Grind_size; i++){
            if(board[row][i]==number){
                return true;
            }
        }
        return false;
    }

    private static boolean isNumberinColumn(int[][] board, int number, int column){
        for(int i=0; i<Grind_size; i++){
            if(board[i][column]==number){
                return true;
            }
        }
        return false;
    }

    private static boolean isNumberinBox(int[][] board, int number, int column, int row){
        int localBoxRow=row-row%3;
        int localBoxColumn=column-column%3;

        for(int i=localBoxRow; i<localBoxRow+3;i++){
            for(int j=localBoxColumn; j<localBoxColumn+3;j++){
                if(board[i][j]==number){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValidPlacement(int[][] board, int number, int column, int row){
        return !isNumberinBox(board, number, column, row) &&
                !isNumberinRow(board, number, row) &&
                !isNumberinColumn(board, number, column);
    }

    private static void sudokuboard(int[][] board, String[] stg){
        System.out.println(stg.length);
        for(int i=0; i<stg.length/3;i++){
            System.out.println(stg[i*3]);
            System.out.println(Integer.parseInt(stg[i*3]));
            board[ Integer.parseInt(stg[i*3])-1][Integer.parseInt(stg[i*3+1])-1]=Integer.parseInt(stg[i*3+2]);

        }
    }


    private static boolean solveSudoku(int[][] board){
        for(int row=0; row<Grind_size; row++){
            for(int column=0; column<Grind_size; column++){
                if(board[row][column]==0){
                    for(int numberToTry=1;numberToTry<=Grind_size; numberToTry++){
                        if(isValidPlacement(board, numberToTry, column, row)){
                            board[row][column]=numberToTry;

                            if(solveSudoku(board)){
                                return true;
                            }else{
                                board[row][column]=0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }


}
