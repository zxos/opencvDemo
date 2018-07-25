package com.gjdl.carameaccept

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

fun Context.showToast(str: String){
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}

fun Context.getSp():SharedPreferences{
    return this.getSharedPreferences("carame_sp", Context.MODE_PRIVATE)
}