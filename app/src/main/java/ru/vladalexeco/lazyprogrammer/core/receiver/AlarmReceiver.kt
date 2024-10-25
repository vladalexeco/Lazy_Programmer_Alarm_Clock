package ru.vladalexeco.lazyprogrammer.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.vladalexeco.lazyprogrammer.presentation.activities.MainActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val mainIntent = Intent(context, MainActivity::class.java)
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        mainIntent.putExtra("navigateTo", "alarm")
        context?.startActivity(mainIntent)
    }
}