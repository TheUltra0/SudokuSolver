package com.example.sudokusolver

import com.example.sudokufree.Numbers
import com.example.sudokufree.NumbersPlayer
import com.example.sudokufree.SudokuGame

class Kotlin {
    val Grind_size = 9

    fun main(arg:String) {

        val board = arrayOf(
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        )
        sudokuboard(board, arg)
        if (solveSudoku(board)) {
            println("Solved successfully!")
        } else {
            println("Unsolvable board :(")
        }

        printBoard(board)
        getSudoku(board)
    }

    private fun printBoard(board: Array<IntArray>) {
        for (row in 0 until Grind_size) {
            for (column in 0 until Grind_size) {
                print(board[row][column])
            }
            println()
        }
    }

    private fun isNumberinRow(board: Array<IntArray>, number: Int, row: Int): Boolean {
        for (i in 0 until Grind_size) {
            if (board[row][i] == number) {
                return true
            }
        }
        return false
    }

    private fun isNumberinColumn(board: Array<IntArray>, number: Int, column: Int): Boolean {
        for (i in 0 until Grind_size) {
            if (board[i][column] == number) {
                return true
            }
        }
        return false
    }

    private fun isNumberinBox(board: Array<IntArray>, number: Int, column: Int, row: Int): Boolean {
        val localBoxRow = row - row % 3
        val localBoxColumn = column - column % 3
        for (i in localBoxRow until localBoxRow + 3) {
            for (j in localBoxColumn until localBoxColumn + 3) {
                if (board[i][j] == number) {
                    return true
                }
            }
        }
        return false
    }

    private fun isValidPlacement(
        board: Array<IntArray>,
        number: Int,
        column: Int,
        row: Int
    ): Boolean {
        return !isNumberinBox(board, number, column, row) &&
                !isNumberinRow(board, number, row) &&
                !isNumberinColumn(board, number, column)
    }

    private fun sudokuboard(board: Array<IntArray>, arg:String) {
        for (i in 0 until arg.length / 3) {
            board[arg[i*3].toInt()-49][arg[i*3+1].toInt()-49]=arg[i*3+2].toInt()-48
        }
    }


    private fun solveSudoku(board: Array<IntArray>): Boolean {
        for (row in 0 until Grind_size) {
            for (column in 0 until Grind_size) {
                if (board[row][column] == 0) {
                    for (numberToTry in 1..Grind_size) {
                        if (isValidPlacement(board, numberToTry, column, row)) {
                            board[row][column] = numberToTry
                            if (solveSudoku(board)) {
                                return true
                            } else {
                                board[row][column] = 0
                            }
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    private fun getSudoku(board: Array<IntArray>) {
        SudokuGame.numbersplayer.forEach{e-> SudokuGame.numbers.remove(Numbers(e.col, e.row, e.number)) }
        SudokuGame.numbersplayer.clear()
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                SudokuGame.numbersplayer.add(NumbersPlayer(i, j, board[j][i]))
            }
        }

        SudokuGame.numbersproblem.forEach { e-> SudokuGame.numbersplayer.remove(SudokuGame.numberat(e.col, e.row))}
        SudokuGame.numbersplayer.forEach{e-> SudokuGame.numbers.add(Numbers(e.col, e.row, e.number)) }
    }

}