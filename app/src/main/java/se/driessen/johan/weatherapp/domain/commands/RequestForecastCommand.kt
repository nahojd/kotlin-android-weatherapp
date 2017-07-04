package se.driessen.johan.weatherapp.domain.commands

import se.driessen.johan.weatherapp.domain.datasource.ForecastProvider
import se.driessen.johan.weatherapp.domain.model.ForecastList

class RequestForecastCommand(val zipCode: Long,
                             val forecastProvider: ForecastProvider = ForecastProvider()) : Command<ForecastList> {

	companion object {
		val DAYS = 7
	}

	override fun execute(): ForecastList {
		return forecastProvider.requestByZipCode(zipCode, DAYS)
	}
}