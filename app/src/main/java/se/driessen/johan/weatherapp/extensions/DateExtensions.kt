package se.driessen.johan.weatherapp.extensions

import java.text.DateFormat
import java.util.*

fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String {
	val dt = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
	return dt.format(this)
}