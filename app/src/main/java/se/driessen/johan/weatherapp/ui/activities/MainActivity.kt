package se.driessen.johan.weatherapp.ui.activities

import android.annotation.TargetApi
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import se.driessen.johan.weatherapp.R
import se.driessen.johan.weatherapp.domain.commands.RequestForecastCommand
import se.driessen.johan.weatherapp.domain.model.ForecastList
import se.driessen.johan.weatherapp.extensions.DelegatesExt
import se.driessen.johan.weatherapp.ui.adapters.ForecastListAdapter


class MainActivity : AppCompatActivity(), ToolbarManager {

	override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

	val zipCode: Long by DelegatesExt.preference(this, SettingsActivity.ZIP_CODE, SettingsActivity.DEFAULT_ZIP)

	@TargetApi(21)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initToolbar()

		forecastList.layoutManager = LinearLayoutManager(this)
		attachToScroll(forecastList)

		supportsLollipop {
			window.statusBarColor = Color.DKGRAY
		}
	}

	override fun onResume() {
		super.onResume()

		loadForecast()
	}

	private fun loadForecast() = async(UI) {
		val result = bg { RequestForecastCommand(zipCode).execute() }
		updateUI(result.await())
	}

	private fun updateUI(weekForecast: ForecastList) {
		val adapter = ForecastListAdapter(weekForecast) {
			startActivity<DetailActivity>(DetailActivity.ID to it.id, DetailActivity.CITY_NAME to weekForecast.city)
		}
		forecastList.adapter = adapter
		toolbarTitle = "${weekForecast.city} (${weekForecast.country})"
	}


	/*
	private fun loadForecast() {
		doAsync {
			val result = RequestForecastCommand(zipCode).execute()

			uiThread {
				val adapter = ForecastListAdapter(result, {
					startActivity<DetailActivity>(DetailActivity.ID to it.id, DetailActivity.CITY_NAME to result.city)
				})
				forecastList.adapter = adapter
				toolbarTitle = "${result.city} (${result.country})"
			}
		}
	}
	*/
}


inline fun supportsLollipop(code: () -> Unit) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
		code()
	}
}