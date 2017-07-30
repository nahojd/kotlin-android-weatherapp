package se.driessen.johan.weatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.ctx
import org.jetbrains.anko.find
import se.driessen.johan.weatherapp.R
import se.driessen.johan.weatherapp.domain.commands.RequestDayForecastCommand
import se.driessen.johan.weatherapp.domain.model.Forecast
import se.driessen.johan.weatherapp.extensions.color
import se.driessen.johan.weatherapp.extensions.textColor
import se.driessen.johan.weatherapp.extensions.toDateString
import java.text.DateFormat

class DetailActivity : AppCompatActivity(), ToolbarManager {

	companion object {
		val ID = "DetailActivity:id"
		val CITY_NAME = "DetailActivity:cityName"
	}

	override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)

		initToolbar()
		toolbarTitle = intent.getStringExtra(CITY_NAME)
		enableHomeAsUp { onBackPressed() }

		/*
		doAsync {
			val result = RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()
			uiThread { bindForecast(result) }
		}
		*/

		async(UI) {
			val result = bg { RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute() }
			bindForecast(result.await())
		}
	}

	private fun bindForecast(forecast: Forecast) = with(forecast) {
		Picasso.with(ctx).load(iconUrl).into(icon)
		toolbar.subtitle = date.toDateString(DateFormat.FULL)
		weatherDescription.text = description
		bindWeather(high to maxTemperature, low to minTemperature)
	}

	private fun  bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
		it.second.text = it.first.toString()
		it.second.textColor = color(when (it.first) {
			in -50..0 -> android.R.color.holo_red_dark
			in 0..15 -> android.R.color.holo_orange_dark
			else -> android.R.color.holo_green_dark
		})
	}
}
