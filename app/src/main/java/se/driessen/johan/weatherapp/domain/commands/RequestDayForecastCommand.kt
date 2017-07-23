package se.driessen.johan.weatherapp.domain.commands

import se.driessen.johan.weatherapp.domain.datasource.ForecastProvider
import se.driessen.johan.weatherapp.domain.model.Forecast

class RequestDayForecastCommand(
		val id: Long,
        val forecastProvider: ForecastProvider = ForecastProvider()) : Command<Forecast> {

	override fun execute(): Forecast = forecastProvider.requestForecast(id)
}