package com.example.weatherapp.abstraction.Utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun dateToFullDateName(date: String): String {
    return try {
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
        val day = stringToDate(date)
        sdf.format(day)
    } catch (e: Exception) {
        ""
    }
}

fun dateHHMM(oldDate: String): String{
    val dateFormat: SimpleDateFormat
    when{
        oldDate.length < 4 -> {
            dateFormat = SimpleDateFormat("hmm") }
        else -> {
            dateFormat = SimpleDateFormat("hhmm")
        }
    }
    val dateFormat2 = SimpleDateFormat("hh:mm aa")
    try {
        val date = dateFormat.parse(oldDate)
        return dateFormat2.format(date)
    } catch (e: ParseException) {
        return  ""
    }
}

fun dateToDayNameEEEE(date: String): String {
    return try {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        val day = stringToyyyyMMdd(date)
        sdf.format(day)
    } catch (e: Exception) {
        ""
    }
}

fun stringToDate(date: String): Date {
    return try {
        val formater = SimpleDateFormat("HH:mm a", Locale.getDefault())
        formater.parse(date)
    } catch (e: Exception) {
        Date()
    }
}

fun stringToyyyyMMdd(date: String): Date {
    return try {
        val formater = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formater.parse(date)
    } catch (e: Exception) {
        Date()
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.showKeyboard() {
    postDelayed(100) {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun getHourlyTime(time: String): String{
    when (time) {
        "0" -> {
            return "00:00 am"
        }
        else -> {
            return  dateHHMM(time)
        }
    }
}