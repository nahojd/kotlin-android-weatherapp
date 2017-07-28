package se.driessen.johan.weatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar.*
import se.driessen.johan.weatherapp.R
import se.driessen.johan.weatherapp.extensions.DelegatesExt

class SettingsActivity : AppCompatActivity() {

	companion object {
		val ZIP_CODE = "zipCode"
		val DEFAULT_ZIP = 12068L
	}

	var zipCode: Long by DelegatesExt.preference(this, ZIP_CODE, DEFAULT_ZIP)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_settings)

		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		cityCode.setText(zipCode.toString())
	}

	override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
		android.R.id.home -> {
			onBackPressed()
			true
		}
		else -> false
	}

	override fun onBackPressed() {
		super.onBackPressed()

		zipCode = cityCode.text.toString().toLong()
	}
}
