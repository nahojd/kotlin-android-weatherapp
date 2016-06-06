package se.driessen.johan.weatherapp.domain.commands

/**
 * Created by johan on 2016-06-06.
 */
interface Command<T> {
	fun execute(): T
}