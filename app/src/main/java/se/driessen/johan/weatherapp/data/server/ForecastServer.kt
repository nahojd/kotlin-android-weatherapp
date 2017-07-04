package se.driessen.johan.weatherapp.data.server

import se.driessen.johan.weatherapp.data.db.ForecastDb
import se.driessen.johan.weatherapp.domain.datasource.ForecastDataSource
import se.driessen.johan.weatherapp.domain.model.ForecastList

class ForecastServer(val dataMapper: ServerDataMapper = ServerDataMapper(),
                     val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {

	override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
		val result = ForecastByZipCodeRequest(zipCode).execute()
		val converted = dataMapper.convertToDomain(zipCode, result)

		forecastDb.saveForecast(converted)
		return forecastDb.requestForecastByZipCode(zipCode, date)
	}
}