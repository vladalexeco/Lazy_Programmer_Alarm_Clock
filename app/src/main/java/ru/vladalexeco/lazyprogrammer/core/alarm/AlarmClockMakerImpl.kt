package ru.vladalexeco.lazyprogrammer.core.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.vladalexeco.lazyprogrammer.core.receiver.AlarmReceiver
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm
import java.util.Calendar

class AlarmClockMakerImpl(private val context: Context) : AlarmClockMaker {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val daysOfWeek = listOf(
        Calendar.MONDAY,
        Calendar.TUESDAY,
        Calendar.WEDNESDAY,
        Calendar.THURSDAY,
        Calendar.FRIDAY,
        Calendar.SATURDAY,
        Calendar.SUNDAY
    )

    @SuppressLint("ScheduleExactAlarm")
    override fun createWeeklyAlarm(alarm: Alarm) {

        for ((index, isActive) in alarm.weekdays.withIndex()) {
            if (isActive) {
                scheduleAlarmForDay(alarm, daysOfWeek[index])
            }
        }
    }

    override fun editAlarm(alarm: Alarm) {

    }

    override fun cancelAlarm(alarm: Alarm) {

        val daysOfWeekWithActiveAlarm = ArrayList<Int>()

        for ((index, day) in daysOfWeek.withIndex()) {
            if (alarm.weekdays[index]) {
                daysOfWeekWithActiveAlarm.add(day)
            }
        }

        for (dayOfWeek in daysOfWeekWithActiveAlarm) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                alarm.id.hashCode() + dayOfWeek,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.cancel(pendingIntent)
        }
    }

    fun scheduleAlarmForDay(alarm: Alarm, dayOfWeek: Int) {

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, alarm.hour.toInt())
            set(Calendar.MINUTE, alarm.minute.toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.DAY_OF_WEEK, dayOfWeek)

            if (before(Calendar.getInstance())) {
                add(Calendar.WEEK_OF_YEAR, 1)
            }
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("alarmId", alarm.id)
            putExtra("hour", alarm.hour)
            putExtra("minute", alarm.minute)
            putExtra("weekdays", alarm.weekdays.toBooleanArray())
            putExtra("isExtended", alarm.isExtended)
            putExtra("isActivated", alarm.isActivated)
            putExtra("melody", alarm.melody)
            putExtra("dayOfWeek", dayOfWeek)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.id.hashCode() + dayOfWeek,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}