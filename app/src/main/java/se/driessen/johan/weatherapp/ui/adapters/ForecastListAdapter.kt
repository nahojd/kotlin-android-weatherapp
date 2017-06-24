package se.driessen.johan.weatherapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import kotlinx.android.synthetic.main.item_forecast.view.*
import se.driessen.johan.weatherapp.R
import se.driessen.johan.weatherapp.domain.model.Forecast
import se.driessen.johan.weatherapp.domain.model.ForecastList
import se.driessen.johan.weatherapp.ui.utils.ctx

class ForecastListAdapter(val weekForecast: ForecastList, val itemClick: (Forecast) -> Unit)
	: RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
		val view = LayoutInflater.from(parent.ctx)
			.inflate(R.layout.item_forecast, parent, false)

		return ViewHolder(view, itemClick)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bindForecast(weekForecast[position])
	}

	override fun getItemCount() = weekForecast.size()

	class ViewHolder(val view: View, val itemClick: (Forecast) -> Unit) : RecyclerView.ViewHolder(view) {

		fun bindForecast(forecast: Forecast) {
			with(forecast) {
				Picasso.with(itemView.ctx).load(iconUrl).into(itemView.icon)
				itemView.date.text = date
				itemView.description.text = description
				itemView.maxTemperature.text = "${high.toString()}°"
				itemView.minTemperature.text = "${low.toString()}°"
				itemView.setOnClickListener { itemClick(this) }
			}
		}
	}
}

