package com.jemandandere.photogallery.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.os.*
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.*
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.util.Constants
import com.jemandandere.photogallery.util.Constants.TAG
import com.jemandandere.photogallery.util.Utils

class GeoPositionService : Service() {
    private val binder: IBinder = LocalBinder()
    private var changingConfiguration = false
    private var notificationManager: NotificationManager? = null

    private var locationRequest: LocationRequest? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var locationCallback: LocationCallback? = null
    private var serviceHandler: Handler? = null

    private var currentLocation: Location? = null

    override fun onCreate() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                onNewLocation(locationResult.lastLocation)
            }
        }

        createLocationRequest()
        updateLocation()

        val handlerThread = HandlerThread(TAG)
        handlerThread.start()
        serviceHandler = Handler(handlerThread.looper)
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.app_name)
            val mChannel = NotificationChannel(
                Constants.GEO_SERVICE_CHANNEL_ID,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager!!.createNotificationChannel(mChannel)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        changingConfiguration = true
    }

    override fun onBind(intent: Intent?): IBinder {
        stopForeground(true)
        changingConfiguration = false
        return binder
    }

    override fun onRebind(intent: Intent?) {
        stopForeground(true)
        changingConfiguration = false
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        if (!changingConfiguration && Utils.requestingLocationUpdates(this)) {
            startForeground(Constants.NOTIFICATION_ID, notification)
        }
        return true
    }

    override fun onDestroy() {
        serviceHandler?.removeCallbacksAndMessages(null)
    }

    fun requestLocationUpdates() {
        Utils.setRequestingLocationUpdates(this, true)
        startService(
            Intent(
                applicationContext,
                GeoPositionService::class.java
            )
        )
        try {
            fusedLocationClient?.requestLocationUpdates(
                locationRequest, locationCallback, Looper.myLooper()
            )
        } catch (unlikely: SecurityException) {
            Utils.setRequestingLocationUpdates(this, false)
        }
    }

    fun removeLocationUpdates() {
        try {
            fusedLocationClient?.removeLocationUpdates(locationCallback)
            Utils.setRequestingLocationUpdates(this, false)
            stopSelf()
        } catch (unlikely: SecurityException) {
            Utils.setRequestingLocationUpdates(this, true)
        }
    }

    private val notification: Notification
        get() {
            return NotificationCompat.Builder(this, Constants.GEO_SERVICE_CHANNEL_ID)
                .setContentText(Utils.getLocationText(currentLocation))
                .setContentTitle(Utils.getLocationTitle(this))
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(Utils.getLocationText(currentLocation))
                .setWhen(System.currentTimeMillis())
                .build()
        }

    private fun updateLocation() {
        try {
            fusedLocationClient?.lastLocation
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        currentLocation = task.result
                    }
                }
        } catch (unlikely: SecurityException) {
        }
    }

    private fun onNewLocation(location: Location) {
        currentLocation = location

        val intent = Intent(Constants.GEO_SERVICE_MESSAGE_BROADCAST)
        intent.putExtra(Constants.GEO_SERVICE_LOCATION_KEY, location)
        LocalBroadcastManager.getInstance(applicationContext)
            .sendBroadcast(intent)

        if (serviceIsRunningInForeground(this)) {
            notificationManager!!.notify(
                Constants.NOTIFICATION_ID,
                notification
            )
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = Constants.UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = Constants.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    inner class LocalBinder : Binder() {
        val service: GeoPositionService
            get() = this@GeoPositionService
    }

    private fun serviceIsRunningInForeground(context: Context): Boolean {
        val manager = context.getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (javaClass.name == service.service.className) {
                if (service.foreground) {
                    return true
                }
            }
        }
        return false
    }
}