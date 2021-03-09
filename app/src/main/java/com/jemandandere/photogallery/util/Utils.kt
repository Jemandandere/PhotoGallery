package com.jemandandere.photogallery.util

import android.content.Context
import android.location.Location
import androidx.preference.PreferenceManager
import java.text.DateFormat
import java.util.*

object Utils {
    const val KEY_REQUESTING_LOCATION_UPDATES = "requesting_location_updates"

    fun setRequestingLocationUpdates(context: Context?, requestingLocationUpdates: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
            .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
            .apply()
    }

    fun requestingLocationUpdates(context: Context?): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false)
    }

    fun getLocationText(location: Location?): String {
        return if (location == null) "Unknown location" else "(" + location.getLatitude()
            .toString() + ", " + location.getLongitude().toString() + ")"
    }

    fun getLocationTitle(context: Context): String {
        return "Location Updated: " +
                DateFormat.getDateTimeInstance().format(Date())
    }
}