package se.driessen.johan.weatherapp.domain.commands

import se.driessen.johan.weatherapp.data.server.ForecastRequest
import se.driessen.johan.weatherapp.domain.mappers.ForecastDataMapper
import se.driessen.johan.weatherapp.domain.model.ForecastList

/**
 * Created by johan on 2016-06-06.
 */
class RequestForecastCommand(private val zipCode: Long): Command<ForecastList> {

	override fun execute(): ForecastList {
		val forecastRequest = ForecastRequest(zipCode)
		return ForecastDataMapper().convertFromDataModel(zipCode, forecastRequest.execute())
	}
}