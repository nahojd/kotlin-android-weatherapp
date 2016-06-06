package se.driessen.johan.weatherapp.data

import android.util.Log
import java.net.URL

/**
 * Created by johan on 2016-06-06.
 */
class Request(val url: String) {

	fun run() {
		val forecastJsonStr = URL(url).readText()
		Log.d(javaClass.simpleName, forecastJsonStr)
	}
}