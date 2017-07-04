package se.driessen.johan.weatherapp.domain.datasource

import se.driessen.johan.weatherapp.domain.model.ForecastList

interface ForecastDataSource {
	fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
}