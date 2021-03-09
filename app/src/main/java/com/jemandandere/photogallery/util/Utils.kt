package com.jemandandere.photogallery.util

import android.content.Context
import android.location.Location
import androidx.preference.PreferenceManager
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    // GeoPositionService
    fun setRequestingLocationUpdates(context: Context?, requestingLocationUpdates: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
            .putBoolean(Constants.REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates)
            .apply()
    }

    fun requestingLocationUpdates(context: Context?): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(Constants.REQUESTING_LOCATION_UPDATES_KEY, false)
    }

    fun getLocationTitle(context: Context): String {
        return "Местоположение в " + SimpleDateFormat("HH:mm:ss").format(Date())
    }

    fun getLocationText(location: Location?): String {
        return if (location == null) "Неизвестное местоположение" else
            "(" + location.latitude.toString() + ", " + location.longitude.toString() + ")"
    }
}