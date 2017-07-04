package se.driessen.johan.weatherapp.domain.commands

interface Command<T> {
	fun execute(): T
}