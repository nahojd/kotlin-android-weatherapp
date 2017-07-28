package se.driessen.johan.weatherapp.data.db

import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import se.driessen.johan.weatherapp.domain.datasource.ForecastDataSource
import se.driessen.johan.weatherapp.domain.model.Forecast
import se.driessen.johan.weatherapp.domain.model.ForecastList
import se.driessen.johan.weatherapp.extensions.*

class ForecastDb (
	val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
	val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {

	override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
		val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"

		val dailyForecast = select(DayForecastTable.NAME)
				.whereSimple(dailyRequest, zipCode.toString(), date.toString())
				.parseList { DayForecast(HashMap(it)) }

		val city = select(CityForecastTable.NAME)
				.whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
				.parseOpt { CityForecast(HashMap(it), dailyForecast) }

		city?.let { dataMapper.convertToDomain(it) } 
	}

	override fun requestDayForecast(id: Long): Forecast? = forecastDbHelper.use {
		val forecast = select(DayForecastTable.NAME).byId(id).parseOpt { DayForecast(HashMap(it)) }
		forecast?.let { dataMapper.convertDayToDomain(it) }
	}

	fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {
		clear(CityForecastTable.NAME)
		clear(DayForecastTable.NAME)

		with(dataMapper.convertFromDomain(forecast)) {
			insert(CityForecastTable.NAME, *map.toVarargArray())
			dailyForecast.forEach {
				insert(DayForecastTable.NAME, *it.map.toVarargArray())
			}
		}
	}
}



