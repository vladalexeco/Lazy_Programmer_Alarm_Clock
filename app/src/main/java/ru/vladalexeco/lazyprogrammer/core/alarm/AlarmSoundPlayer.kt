package ru.vladalexeco.lazyprogrammer.core.alarm

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager

object AlarmSoundPlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun start(context: Context) {
        if (mediaPlayer == null) {
            val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

            mediaPlayer = MediaPlayer.create(context, ringtoneUri).apply {
                isLooping = true
                start()
            }
        }
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}