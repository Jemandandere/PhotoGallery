package com.jemandandere.photogallery.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.jemandandere.photogallery.R

class MediaService: Service() {

    private var player: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(applicationContext, R.raw.terry_s_taylor_dum_da_dum_doi_doi)
        player?.let {
            it.isLooping = true
            it.start()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        player?.let {
            it.stop()
            it.release()
        }
        player = null
        super.onDestroy()
    }
}