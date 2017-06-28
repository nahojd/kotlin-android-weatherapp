package se.driessen.johan.weatherapp.ui

import android.app.Application
import se.driessen.johan.weatherapp.extensions.DelegatesExt

class App : Application() {
	companion object {
		var instance: App by DelegatesExt.notNullSingleValue()
	}

	override fun onCreate() {
		super.onCreate()
		instance = this
	}
}