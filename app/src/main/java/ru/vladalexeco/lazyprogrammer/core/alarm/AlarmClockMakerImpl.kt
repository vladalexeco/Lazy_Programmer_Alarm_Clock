package ru.vladalexeco.lazyprogrammer.core.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.vladalexeco.lazyprogrammer.core.receiver.AlarmReceiver
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm

class AlarmClockMakerImpl(private val context: Context) : AlarmClockMaker {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @SuppressLint("ScheduleExactAlarm")
    override fun createAlarm(alarm: Alarm, triggerTime: Long) {

        val intent = Intent(context, AlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }

    override fun editAlarm(alarm: Alarm) {

    }

    override fun cancelAlarm(alarm: Alarm) {

        val intent = Intent(context, AlarmReceiver::class.java)

        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarm.hashCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }
}