package se.driessen.johan.weatherapp.ui.activities

import android.annotation.TargetApi
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
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

		doAsync {
			val result = RequestForecastCommand(12068).execute()

			uiThread {
				forecastList.adapter = ForecastListAdapter(result, {
					startActivity<DetailActivity>(DetailActivity.ID to it.id, DetailActivity.CITY_NAME to result.city)
				})

			}
		}
	}
}


inline fun supportsLollipop(code: () -> Unit) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
		code()
	}
}