package se.driessen.johan.weatherapp.ui.activities

import android.annotation.TargetApi
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import se.driessen.johan.weatherapp.R
import se.driessen.johan.weatherapp.domain.commands.RequestForecastCommand
import se.driessen.johan.weatherapp.ui.adapters.ForecastListAdapter


class MainActivity : AppCompatActivity() {

	@TargetApi(21)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		forecastList.layoutManager = LinearLayoutManager(this)

		supportsLollipop {
			window.statusBarColor = Color.DKGRAY
		}

		doAsync() {
			val result = RequestForecastCommand("Stockholm,se").execute()

			uiThread {
				forecastList.adapter = ForecastListAdapter(result, { toast(it.date) })

			}
		}
	}
}


inline fun supportsLollipop(code: () -> Unit) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
		code()
	}
}