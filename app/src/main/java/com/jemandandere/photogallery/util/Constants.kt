package com.jemandandere.photogallery.util

object Constants {
    // Base
    private const val PACKAGE_NAME = "com.jemandandere.photogallery"
    private const val MOST_FAVORITE_NUMBER = 18497

    // Log
    const val TAG = "PhotoGallery"
    const val ERROR = "Something wrong"

    // URLs
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    const val ALBUM_LIST_PATH = "/albums"
    const val PHOTO_LIST_PATH = "/photos"

    // Bundle Keys
    const val ALBUM_KEY = "albumKey"
    const val REQUESTING_LOCATION_UPDATES_KEY = "requestingLocationUpdatesKey"

    // GeoPositionService
    const val GEO_SERVICE_CHANNEL_ID = "channel_$MOST_FAVORITE_NUMBER"
    const val GEO_SERVICE_MESSAGE_BROADCAST = "$PACKAGE_NAME.broadcast"
    const val GEO_SERVICE_LOCATION_KEY = "$PACKAGE_NAME.location"

    const val REQUEST_PERMISSIONS_REQUEST_CODE = MOST_FAVORITE_NUMBER

    const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 5000
    const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
        UPDATE_INTERVAL_IN_MILLISECONDS / 5

    const val NOTIFICATION_ID = MOST_FAVORITE_NUMBER
}