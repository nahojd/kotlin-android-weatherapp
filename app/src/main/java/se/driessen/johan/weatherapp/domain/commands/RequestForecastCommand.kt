package se.driessen.johan.weatherapp.domain.commands

import se.driessen.johan.weatherapp.data.ForecastRequest
import se.driessen.johan.weatherapp.domain.mappers.ForecastDataMapper
import se.driessen.johan.weatherapp.domain.model.ForecastList

/**
 * Created by johan on 2016-06-06.
 */
class RequestForecastCommand(val query: String): Command<ForecastList> {

	override fun execute(): ForecastList {
		val forecastRequest = ForecastRequest(query)
		return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
	}
}