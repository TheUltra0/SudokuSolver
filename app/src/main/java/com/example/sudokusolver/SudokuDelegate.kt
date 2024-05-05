package com.example.sudokufree

interface SudokuDelegate {
    fun sudokuColorSquare(col:Int, row:Int)
    fun numberplayer(col:Int, row:Int, number:Int)
    fun numberat(col:Int, row:Int): NumbersPlayer?
    fun readstring(string:String)
    fun numberproblemat(col:Int, row:Int): NumbersProblem?
    fun findnumber(col:Int, row:Int): Numbers?
    fun numberwrongplayer(col:Int, row:Int, number:Int)
    fun findwrongnumber(col:Int, row:Int): NumberWrongPlayer?
    fun wronglines(string:String, int:Int)
    fun findnumberprob(col: Int, row: Int, number2: Int): Numbersprob?
    fun numberprob(col: Int, row: Int, number: Int)
    fun clear()
}