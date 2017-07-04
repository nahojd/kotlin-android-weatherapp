package se.driessen.johan.weatherapp.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import se.driessen.johan.weatherapp.ui.App

class ForecastDbHelper(ctx: Context = App.instance) :
		ManagedSQLiteOpenHelper(ctx, ForecastDbHelper.DB_NAME, null, ForecastDbHelper.DB_VERSION) {

	companion object {
		val DB_NAME = "forecast.db"
		val DB_VERSION = 1
		val instance by lazy { ForecastDbHelper() }
	}

	override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
		db.dropTable(CityForecastTable.NAME, true)
		db.dropTable(DayForecastTable.NAME, true)
		onCreate(db)
	}

	override fun onCreate(db: SQLiteDatabase) {
		db.createTable(CityForecastTable.NAME, true,
				CityForecastTable.ID to INTEGER + PRIMARY_KEY,
				CityForecastTable.CITY to TEXT,
				CityForecastTable.COUNTRY to TEXT)

		db.createTable(DayForecastTable.NAME, true,
				//This currently does not work, although it should.
				//See issue https://github.com/Kotlin/anko/issues/409
				//DayForecastTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
				DayForecastTable.ID to SqlType.create("INTEGER PRIMARY KEY AUTOINCREMENT"),
				DayForecastTable.DATE to INTEGER,
				DayForecastTable.DESCRIPTION to TEXT,
				DayForecastTable.HIGH to INTEGER,
				DayForecastTable.LOW to INTEGER,
				DayForecastTable.ICON_URL to TEXT,
				DayForecastTable.CITY_ID to INTEGER)
	}
}