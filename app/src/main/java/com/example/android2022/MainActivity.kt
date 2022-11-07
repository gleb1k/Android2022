package com.example.android2022

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.TimePicker
import android.widget.Toast
import com.example.android2022.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var calendar = Calendar.getInstance()
    private var alarmManager: AlarmManager? = null
    private var pendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        MyNotificationChannel(this).createNotificationChannel()

        binding?.run {
            btnStart.setOnClickListener {
                startAlarm()
            }
            btnStop.setOnClickListener {
                stopAlarm()
            }
            tvTimePicker.setOnClickListener {
                callTimePickerDialog()
            }
            tvDatePicker.setOnClickListener {
                callDatePickerDialog()
            }
        }
    }

    private fun startAlarm() {

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pendingIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)
            } else {
                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            }
        }
        alarmManager?.set(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        Toast.makeText(this, "Будильник установлен!", Toast.LENGTH_LONG).show()

//        val minute = calendar.get(Calendar.MINUTE)
//        val hour = calendar.get(Calendar.HOUR_OF_DAY)
//        val second = calendar.get(Calendar.SECOND)
//        val milis = calendar.get(Calendar.MILLISECOND)
    }

    private fun stopAlarm() {

        alarmManager?.cancel(pendingIntent)

        Toast.makeText(this, "Будильник выключен", Toast.LENGTH_LONG).show()

    }

    private fun callTimePickerDialog() {

        val timePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {

            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                binding?.tvTimePicker?.text = String.format("%02d : %02d", hourOfDay, minute)
            }

        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
        timePicker.show()
    }

    private fun callDatePickerDialog() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val datePicker = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding?.tvDatePicker?.text = sdf.format(calendar.time)

        }, year, month, day)
        datePicker.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        alarmManager = null
    }
}