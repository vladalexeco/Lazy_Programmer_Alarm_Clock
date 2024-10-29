package ru.vladalexeco.lazyprogrammer.core.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.hilt.android.EntryPointAccessors
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.core.alarm.AlarmSoundPlayer
import ru.vladalexeco.lazyprogrammer.core.di.AlarmReceiverEntryPoint
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm
import ru.vladalexeco.lazyprogrammer.presentation.activities.MainActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {

            val entryPoint = EntryPointAccessors.fromApplication(context, AlarmReceiverEntryPoint::class.java)
            val scheduleAlarmForDayUseCase = entryPoint.getScheduleAlarmForDayUseCase()

            val alarmId = intent?.getStringExtra("alarmId") ?: return
            val alarmHour = intent.getStringExtra("hour") ?: "0"
            val alarmMinute = intent.getStringExtra("minute") ?: "0"
            val alarmWeekdays = intent.getBooleanArrayExtra("weekdays") ?:
            booleanArrayOf(true, true, true, true, true, true, true)
            val alarmIsExtended = intent.getBooleanExtra("isExtended", true)
            val alarmIsActivated = intent.getBooleanExtra("isActivated", true)
            val alarmMelody = intent.getStringExtra("melody")

            val dayOfWeek = intent.getIntExtra("dayOfWeek", -1)

            if (dayOfWeek != -1) {
                val alarm = Alarm(
                    id = alarmId,
                    hour = alarmHour,
                    minute = alarmMinute,
                    weekdays = alarmWeekdays.toList(),
                    isExtended = alarmIsExtended,
                    isActivated = alarmIsActivated,
                    melody = alarmMelody
                )

                scheduleAlarmForDayUseCase.invoke(alarm = alarm, dayOfWeek = dayOfWeek)
            }

            AlarmSoundPlayer.start(context)

            showNotification(context)
        }
    }

    private fun showNotification(context: Context) {
        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("navigateTo", "alarm")
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "alarm_channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Будильник")
            .setContentText("Нажмите, чтобы начать выполнять задание")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "alarm_channel_id",
                "Alarm Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(1, notification)
    }
}