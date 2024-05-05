package com.example.sudokusolver

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatDelegate
import com.example.sudokufree.*
import com.example.sudokufree.SudokuGame.numberproblem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.sudoku_view
import kotlinx.android.synthetic.main.colorbuttonsettings.view.*
import kotlinx.android.synthetic.main.sudokusolver.*
import kotlin.math.min
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.view.updateLayoutParams
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlin.random.Random
import kotlin.random.nextInt


class MainActivity : AppCompatActivity(), SudokuDelegate {

    private var adRequest = AdRequest.Builder().build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var savedata = savedata(applicationContext)
        var sharedpref: SharedPreferences = savedata.sharedPreferences
        val editor = sharedpref.edit()

        val vto: ViewTreeObserver = sudoku_view.viewTreeObserver
        vto.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                // Remove after the first run so it doesn't fire forever
                sudoku_view.viewTreeObserver.removeOnPreDrawListener(this)
                var finalWidth = sudoku_view.measuredWidth
                sudoku_view.updateLayoutParams { width=finalWidth
                    height=finalWidth }
                //Log.d(ContentValues.TAG, "$finalHeight $finalWidth")
                return true
            }
        })

        MobileAds.initialize(this){}

        adView.loadAd(adRequest)


        var sudokuView = findViewById<SudokuView>(R.id.sudoku_view)
        sudokuView.SudokuDelegate = this

        var boolean=sharedpref.getBoolean("night_mode", false)

        if(boolean) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }



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

        ColorButton.setOnClickListener {
            colorButton.performClick()
        }

        colorButton.setOnClickListener {
            //Log.d(ContentValues.TAG, "${sudokuView.numbersize}")
            val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.colorbuttonsettings,null)

            when {
                sharedpref.getInt("textsize",2)==1 -> {
                    sudokuView.smalltext()
                    view.smalltextbackground.background=resources.getDrawable(R.drawable.colorbuttonpressed)
                    view.mediumtextbackground.background=resources.getDrawable(R.drawable.colorbuttonnumbers)
                    view.largetextbackground.background=resources.getDrawable(R.drawable.colorbuttonnumbers)
                }
                sharedpref.getInt("textsize",2)==2 -> {
                    sudokuView.mediumtext()
                    view.smalltextbackground.background=resources.getDrawable(R.drawable.colorbuttonnumbers)
                    view.mediumtextbackground.background=resources.getDrawable(R.drawable.colorbuttonpressed)
                    view.largetextbackground.background=resources.getDrawable(R.drawable.colorbuttonnumbers)
                }
                sharedpref.getInt("textsize",2)==3 -> {
                    sudokuView.largetext()
                    view.smalltextbackground.background=resources.getDrawable(R.drawable.colorbuttonnumbers)
                    view.mediumtextbackground.background=resources.getDrawable(R.drawable.colorbuttonnumbers)
                    view.largetextbackground.background=resources.getDrawable(R.drawable.colorbuttonpressed)
                }
            }

            val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                LinearLayout.LayoutParams.WRAP_CONTENT // Window height
            )
            val test1 = IntArray(2)
            ColorButton.getLocationOnScreen(test1)

            popupWindow.isOutsideTouchable = true
            popupWindow.isFocusable = true
            popupWindow.showAtLocation(
                ColorButton, // Location to display popup window
                Gravity.NO_GRAVITY, // Exact position of layout to display popup
                test1[0], // X offset
                test1[1]-ColorButton.height*3// Y offset
            )
            view.lightculorbutton.setOnClickListener {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                //Log.d(ContentValues.TAG, "yeyeyeye")
                editor.apply{
                    putBoolean("night_mode", false)
                    apply()
                }

            }
            view.nightcolorbutton.setOnClickListener {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.apply{
                    putBoolean("night_mode", true)
                    apply()
                }

            }
            view.smalltext.setOnClickListener {
                sudokuView.smalltext()
                view.smalltextbackground.background=resources.getDrawable(R.drawable.colorbuttonpressed)
                view.mediumtextbackground.background=resources.getDrawable(R.drawable.colorbuttonnumbers)
                view.largetextbackground.background=resources.getDrawable(R.drawable.colorbuttonnumbers)
                editor.apply {
                    putInt("textsize", 1)
                    apply()
                }

            }
            view.mediumtext.setOnClickListener {
                sudokuView.mediumtext()
                view.smalltextbackground.background=resources.getDrawable(R.drawable.colorbuttonnumbers)
                view.mediumtextbackground.background=resources.getDrawable(R.drawable.colorbuttonpressed)
                view.largetextbackground.background=resources.getDrawable(R.drawable.colorbuttonnumbers)
                editor.apply {
                    putInt("textsize", 2)
                    apply()
                }
            }
            view.largetext.setOnClickListener {
                sudokuView.largetext()
                view.smalltextbackground.background=resources.getDrawable(R.drawable.colorbuttonnumbers)
                view.mediumtextbackground.background=resources.getDrawable(R.drawable.colorbuttonnumbers)
                view.largetextbackground.background=resources.getDrawable(R.drawable.colorbuttonpressed)
                editor.apply {
                    putInt("textsize", 3)
                    apply()
                }
            }


        }

        stergetiImg.setOnClickListener { stergeti.performClick() }
        stergeti.setOnClickListener {
            stergeti()

            var BgMusic = MediaPlayer.create(applicationContext, R.raw.eraser)
            BgMusic?.start()

            stergetiImg.setImageResource(R.drawable.eraser_anim)
            var anim=stergetiImg.drawable as AnimatedVectorDrawable
            anim.start()


        }

        ClearImg.setOnClickListener {ClearAll.performClick()}
        ClearAll.setOnClickListener {

            var BgMusic = MediaPlayer.create(applicationContext, R.raw.clear)
            BgMusic?.start()

            ClearImg.setImageResource(R.drawable.clearanim)
            var anim=ClearImg.drawable as AnimatedVectorDrawable
            anim.start()

            SudokuGame.numbersproblem.clear()
            SudokuGame.numbersplayer.clear()
            SudokuGame.numbers.clear()
            SudokuGame.numberswrongplayer.clear()
            SudokuGame.wronglines.clear()
        }

        SolveImg.setOnClickListener {Solve.performClick()}
        Solve.setOnClickListener {

            var BgMusic = MediaPlayer.create(applicationContext, R.raw.solve)
            BgMusic?.start()

            SolveImg.setImageResource(R.drawable.solveanim)
            var anim=SolveImg.drawable as AnimatedVectorDrawable
            anim.start()

            if(SudokuGame.numberswrongplayer.isNotEmpty()){
                Toast.makeText(applicationContext, "There is no solution.", Toast.LENGTH_SHORT).show()
            }else{
                sudokuView.stringreset()
                SudokuGame.numbers.forEach {e->
                    sudokuView.string((e.row+1).toString())
                    sudokuView.string((e.col+1).toString())
                    sudokuView.string((e.number).toString())
                }

                if(SudokuGame.numbers.size!=81){
                    Kotlin().main(sudokuView.stringneed)
                }


            }
        }

    }

    fun stergeti(){
        var savedata = savedata(applicationContext)
        var sharedpref: SharedPreferences = savedata.sharedPreferences
        val editor = sharedpref.edit()
        SudokuGame.sudokuColorSquare.forEach { e ->
            SudokuGame.numbersproblem.remove(
                SudokuGame.numberproblemat(
                    e.col,
                    e.row
                )
            )
            SudokuGame.numbersplayer.remove(
                SudokuGame.numberat(
                    e.col,
                    e.row
                )
            )
            SudokuGame.numbers.remove(
                SudokuGame.findnumber(
                    e.col,
                    e.row
                )
            )
            SudokuGame.numberswrongplayer.remove(SudokuGame.findwrongnumber(e.col, e.row))
            removewrongnumbers()
            wronglines()
        }
    }


    fun onClick(view: View) {

        var savedata = savedata(applicationContext)
        var sharedpref: SharedPreferences = savedata.sharedPreferences
        val editor = sharedpref.edit()

        var sudokuView = findViewById<SudokuView>(R.id.sudoku_view)
        sudokuView.SudokuDelegate = this

        var stringneed = view.toString().let {
            it[it.length - 2]
        }

        SudokuGame.sudokuColorSquare.forEach { e ->
            if (savedata.sharedPreferences.getInt("numberprob", 0) == 0) {
                if (numberat(e.col, e.row) == null && numberproblemat(
                        e.col,
                        e.row
                    ) == null && findwrongnumber(e.col, e.row) == null
                ) {

                    numberproblem(e.col, e.row,stringneed.toInt() - 48)



                    var scoreint=sharedpref.getInt("scoreint", 0)
                    editor.apply {
                        putInt("scoreint", scoreint+savedata.sharedPreferences.getInt("mode", 0) * 100)
                        apply()
                    }

                    var BgMusic = MediaPlayer.create(applicationContext, R.raw.place)
                    BgMusic?.start()


                    wrongnumbers()


                    for (i in 1..9) {
                        SudokuGame.numberprob.remove(Numbersprob(e.col, e.row, i))
                    }
                }
            } else {
                if (findnumberprob(
                        e.col,
                        e.row,
                        stringneed.toInt() - 48
                    ) == null && findnumber(e.col, e.row) == null && findwrongnumber(
                        e.col,
                        e.row
                    ) == null
                ) {
                    numberprob(e.col, e.row, stringneed.toInt() - 48)
                } else if (findnumberprob(e.col, e.row, stringneed.toInt() - 48) != null) {
                    SudokuGame.numberprob.remove(Numbersprob(e.col, e.row, stringneed.toInt() - 48))
                }
            }
        }
        wronglines()

    }

    fun wrongnumbers() {
        SudokuGame.sudokuColorSquare.forEach { e ->
            for (i in 0..8) {
                for (j in 0..8) {
                    if ((j != e.col || i != e.row) && (!(j != e.col && i != e.row) || (j / 3 == e.col / 3 && i / 3 == e.row / 3)) &&
                        findnumber(j, i)?.number == findnumber(e.col, e.row)?.number &&
                        findnumber(j, i)?.number != null
                    ) {
                        //Log.d(ContentValues.TAG, "yeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
                        if (findwrongnumber(j, i) == null) {
                            findnumber(j, i)?.number?.let { numberwrongplayer(j, i, it) }
                        }
                        if (findwrongnumber(e.col, e.row) == null) {
                            findnumber(j, i)?.number?.let { numberwrongplayer(e.col, e.row, it) }
                        }
                        //Log.d(ContentValues.TAG, "${findnumber(e.col, e.row)}")
                        //Log.d(ContentValues.TAG, "${findnumber(j, i)}")
                    }
                }
            }
            //Log.d(ContentValues.TAG, "${SudokuGame.numberswrongplayer}")
        }
    }

    fun wronglines(){
        SudokuGame.wronglines.clear()
        SudokuGame.numberswrongplayer.forEach {e->
            for (i in 0..8) {
                for (j in 0..8) {
                    if ((j == e.col && i != e.row) &&
                        findnumber(j, i)?.number == findnumber(e.col, e.row)?.number &&
                        findnumber(j, i)?.number != null
                    ) {
                        if(SudokuGame.findwronglines("col", j)==null) {
                            wronglines("col", j)
                        }
                    }
                    if ((j != e.col && i == e.row) &&
                        findnumber(j, i)?.number == findnumber(e.col, e.row)?.number &&
                        findnumber(j, i)?.number != null
                    ) {
                        if(SudokuGame.findwronglines("row", i)==null) {
                            wronglines("row", i)
                        }
                    }
                    if ((j != e.col || i != e.row)&&(j / 3 == e.col / 3 && i / 3 == e.row / 3) &&
                        findnumber(j, i)?.number == findnumber(e.col, e.row)?.number &&
                        findnumber(j, i)?.number != null
                    ) {
                        //Log.d(ContentValues.TAG, "Number1: $j, Number 2:$i, Number 3:${e.col}, Number 4:${e.row}")
                        if(SudokuGame.findwronglines("cube", (i/3)*3+(j/3))==null){
                            wronglines("cube", (i/3)*3+(j/3) )
                        }
                    }
                }
            }


        }
        //Log.d(ContentValues.TAG, "${SudokuGame.wronglines}")
    }

    fun removewrongnumbers(){

        SudokuGame.numbers.forEach {e->
            loop@ for (i in 0..8) {
                for (j in 0..8) {
                    if ((j != e.col || i != e.row) && (!(j != e.col && i != e.row) || (j / 3 == e.col / 3 && i / 3 == e.row / 3)) &&
                        findnumber(j, i)?.number == findnumber(e.col, e.row)?.number &&
                        findnumber(j, i)?.number != null
                    ) {
                        break@loop
                    }
                    if(i==8 && j==8){
                        SudokuGame.numberswrongplayer.remove(findwrongnumber(e.col, e.row))
                    }
                }

            }
        }
            //Log.d(ContentValues.TAG, "${SudokuGame.numberswrongplayer}")
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

    override fun onResume() {
        super.onResume()

        adView.loadAd(adRequest)
    }


}