package com.example.sudokusolver

import android.content.Context
import android.content.SharedPreferences

class savedata(context: Context) {
    var sharedPreferences: SharedPreferences = context.getSharedPreferences("file", Context.MODE_PRIVATE)
}