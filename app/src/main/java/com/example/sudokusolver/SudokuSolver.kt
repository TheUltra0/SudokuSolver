package com.example.sudokusolver


import android.content.ContentValues
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.example.sudokufree.*
import com.example.sudokusolver.Java.main
import kotlinx.android.synthetic.main.sudokusolver.*
import kotlin.math.min


class SudokuSolver :AppCompatActivity(), SudokuDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.sudokusolver)

        var sudokuView = findViewById<SudokuView>(R.id.sudoku_view)
        sudokuView.SudokuDelegate = this


        var savedata= savedata(applicationContext)
        var sharedpref: SharedPreferences = savedata.sharedPreferences
        val editor = sharedpref.edit()


        sudoku_view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                sudoku_view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                sudokuView.cellSide = min(sudoku_view.width, sudoku_view.height) * sudokuView.scaleCellSide

                when {
                    sharedpref.getInt("textsize",2)==1 -> {
                        sudokuView.smalltext()
                    }
                    sharedpref.getInt("textsize",2)==2 -> {
                        sudokuView.mediumtext()
                    }
                    sharedpref.getInt("textsize",2)==3 -> {
                        sudokuView.largetext()
                    }
                }}
        })


        if(sharedpref.getInt("dif", 0)==1){
            var  stringneed=sharedpref.getInt("string", 0)
            var string=getString(resources.getIdentifier("p${stringneed}s1","string", packageName))
            solvertext.text="$stringneed"
            numberstext.text="${string.length/3}"
        }else if(sharedpref.getInt("dif", 0)==2){
            var  stringneed=sharedpref.getInt("string", 0)
            var string=getString(resources.getIdentifier("m${stringneed}s1","string", packageName))
            solvertext.text="$stringneed"
            numberstext.text="${string.length/3}"
        }else if(sharedpref.getInt("dif", 0)==3){
            var  stringneed=sharedpref.getInt("string", 0)
            var string=getString(resources.getIdentifier("h${stringneed}s1","string", packageName))
            solvertext.text="$stringneed"
            numberstext.text="${string.length/3}"
        }else{
            var  stringneed=sharedpref.getInt("string", 0)
            var string=getString(resources.getIdentifier("e${stringneed}s1","string", packageName))
            solvertext.text="$stringneed"
            numberstext.text="${string.length/3}"
        }




        solvebutton.setOnClickListener {
            var  stringneed=sharedpref.getInt("string", 0)
            var string=if(sharedpref.getInt("dif", 0)==1) {
                getString(resources.getIdentifier("p${stringneed}s1", "string", packageName))
            }else if(sharedpref.getInt("dif", 0)==2){getString(resources.getIdentifier("m${stringneed}s1", "string", packageName))}
            else if(sharedpref.getInt("dif", 0)==3){getString(resources.getIdentifier("h${stringneed}s1", "string", packageName))}
            else{getString(resources.getIdentifier("e${stringneed}s1", "string", packageName))}
            Log.d(ContentValues.TAG, "$string")
            Kotlin().main(string)
            var a=""
            for(i in 0..8){
                for(j in 0..8){
                    a+=findnumber(j, i)?.number.toString()
                }
            }
            if(sharedpref.getInt("dif", 0)==1) {
                editor.apply {
                    putString("p${stringneed}s2", a)
                    apply()
                }
            }else if (sharedpref.getInt("dif", 0)==2){
                editor.apply {
                    putString("m${stringneed}s2", a)
                    apply()
                }
            }else if (sharedpref.getInt("dif", 0)==3){
                editor.apply {
                    putString("h${stringneed}s2", a)
                    apply()
                }
            } else{
                editor.apply {
                    putString("e${stringneed}s2", a)
                    apply()
                }
            }
            //Log.d(ContentValues.TAG, "${a}")
        }

        nextbutton.setOnClickListener {
            var  stringneed=sharedpref.getInt("string", 0)
            if(stringneed<100){
                clear()
                editor.apply {
                    putInt("string", stringneed+1)
                    apply()
                }
                var string=if(sharedpref.getInt("dif", 0)==1) {
                    getString(resources.getIdentifier("p${sharedpref.getInt("string", 0)}s1", "string", packageName))
                }else if(sharedpref.getInt("dif", 0)==2){getString(resources.getIdentifier("m${sharedpref.getInt("string", 0)}s1", "string", packageName))}
                else if(sharedpref.getInt("dif", 0)==3){getString(resources.getIdentifier("h${sharedpref.getInt("string", 0)}s1", "string", packageName))}
                else {getString(resources.getIdentifier("e${sharedpref.getInt("string", 0)}s1", "string", packageName))}
                readstring(string)
                solvertext.text="${stringneed+1}"
                numberstext.text="${string.length/3}"
            }
        }
        prevbutton.setOnClickListener {
            var  stringneed=sharedpref.getInt("string", 0)
            if(stringneed>1){
                clear()
                editor.apply {
                    putInt("string", stringneed-1)
                    apply()
                }
                var string=if(sharedpref.getInt("dif", 0)==1) {
                    getString(resources.getIdentifier("p${sharedpref.getInt("string", 0)}s1", "string", packageName))
                }else if(sharedpref.getInt("dif", 0)==2){getString(resources.getIdentifier("m${sharedpref.getInt("string", 0)}s1", "string", packageName))}
                else if(sharedpref.getInt("dif", 0)==3){getString(resources.getIdentifier("h${sharedpref.getInt("string", 0)}s1", "string", packageName))}
                else {getString(resources.getIdentifier("e${sharedpref.getInt("string", 0)}s1", "string", packageName))}
                readstring(string)
                solvertext.text="${stringneed-1}"
                numberstext.text="${string.length/3}"
            }
        }



    }

    override fun clear() {
        SudokuGame.clear()
    }

    ////Clear sudoku


    override fun sudokuColorSquare(col: Int, row: Int) {
        SudokuGame.sudokuColorSquare(col, row)
    }

    override fun numberplayer(col: Int, row: Int, number: Int) {
        SudokuGame.numberplayer(col, row, number)
    }

    override fun numberat(col: Int, row: Int): NumbersPlayer? {
        return SudokuGame.numberat(col, row)
    }

    override fun readstring(string: String) {
        SudokuGame.readstring(string)
    }

    override fun numberproblemat(col: Int, row: Int): NumbersProblem? {
        return SudokuGame.numberproblemat(col, row)
    }

    override fun findnumber(col: Int, row: Int): Numbers? {
        return SudokuGame.findnumber(col, row)
    }

    override fun numberwrongplayer(col: Int, row: Int, number: Int) {
        SudokuGame.numberwrongplayer(col, row, number)
    }

    override fun findwrongnumber(col: Int, row: Int): NumberWrongPlayer? {
        return SudokuGame.findwrongnumber(col, row)
    }

    override fun wronglines(string: String, int: Int) {
        SudokuGame.wronglines(string, int)
    }

    override fun findnumberprob(col: Int, row: Int, number2: Int): Numbersprob? {
        return SudokuGame.findnumberprob(col, row, number2)
    }

    override fun numberprob(col: Int, row: Int, number: Int) {
        SudokuGame.numberprob(col, row, number)
    }
}