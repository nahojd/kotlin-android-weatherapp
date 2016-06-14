package se.driessen.johan.weatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.jetbrains.anko.async
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import se.driessen.johan.weatherapp.R
import se.driessen.johan.weatherapp.domain.commands.RequestForecastCommand
import se.driessen.johan.weatherapp.domain.model.Forecast
import se.driessen.johan.weatherapp.ui.adapters.ForecastListAdapter

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val forecastList: RecyclerView = find(R.id.forecast_list)
		forecastList.layoutManager = LinearLayoutManager(this)

		async() {
			val result = RequestForecastCommand("Stockholm,se").execute()

			uiThread {
				forecastList.adapter = ForecastListAdapter(result,
						object : ForecastListAdapter.OnItemClickListener {
							override fun invoke(forecast: Forecast) {
								toast(forecast.date)
							}
						})
			}
		}
	}
}
