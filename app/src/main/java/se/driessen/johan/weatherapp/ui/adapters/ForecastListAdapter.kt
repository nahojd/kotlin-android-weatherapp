package se.driessen.johan.weatherapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import se.driessen.johan.weatherapp.R
import se.driessen.johan.weatherapp.domain.model.Forecast
import se.driessen.johan.weatherapp.domain.model.ForecastList
import se.driessen.johan.weatherapp.ui.utils.ctx

class ForecastListAdapter(val weekForecast: ForecastList, val itemClick: ForecastListAdapter.OnItemClickListener) :
		RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
		var view = LayoutInflater.from(parent.ctx)
			.inflate(R.layout.item_forecast, parent, false)

		return ViewHolder(view, itemClick)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bindForecast(weekForecast[position])
	}

	override fun getItemCount(): Int = weekForecast.size()

	class ViewHolder(val view: View, val itemClick: OnItemClickListener) : RecyclerView.ViewHolder(view) {

		private val iconView: ImageView
		private val dateView: TextView
		private val descriptionView: TextView
		private val maxTemperatureView: TextView
		private val minTemperatureView: TextView

		init {
			iconView = view.find(R.id.icon)
			dateView = view.find(R.id.date)
			descriptionView = view.find(R.id.description)
			maxTemperatureView = view.find(R.id.maxTemperature)
			minTemperatureView = view.find(R.id.minTemperature)
		}

		fun bindForecast(forecast: Forecast) {
			with(forecast) {
				Picasso.with(itemView.ctx).load(iconUrl).into(iconView)
				dateView.text = date
				descriptionView.text = description
				maxTemperatureView.text = "${high.toString()}°"
				minTemperatureView.text = "${low.toString()}°"
				itemView.setOnClickListener { itemClick(this) }
			}
		}
	}

	interface OnItemClickListener {
		operator fun invoke(forecast: Forecast)
	}
}

