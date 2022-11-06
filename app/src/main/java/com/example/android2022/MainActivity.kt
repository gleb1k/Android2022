package com.example.android2022

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import com.example.android2022.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var notificationProvider: NotificationProvider? = null

    private var hours : Int = 0
    private var minutes : Int = 0
//    private var year = Calendar.YEAR
//    private var month = Calendar.MONTH
//    private var day = Calendar.DAY_OF_MONTH

    private var calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        notificationProvider = NotificationProvider(context = this, AwakeActivity())

        binding?.run {
            btnStart.setOnClickListener {
                notificationProvider?.showNotification("AAAhlarm", "Dungeon Master can't wait!!")
            }
            tvTimePicker.setOnClickListener{
                callTimePickerDialog()
            }
            tvDatePicker.setOnClickListener{
                callDatePickerDialog()
            }
        }

    }

    private fun callTimePickerDialog() {

        val timePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {

            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                hours = hourOfDay
                minutes = minute
                binding?.tvTimePicker?.text = String.format("%02d : %02d", hourOfDay, minute)
            }

        }, hours, minutes, true)
        timePicker.show()
    }
    private fun callDatePickerDialog(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val datePicker = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            // Display Selected date in textbox

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding?.tvDatePicker?.text = sdf.format(calendar.time)

        }, year, month, day)

        datePicker.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationProvider = null
    }
}