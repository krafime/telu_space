package com.telu.teluspace

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.MenuItem
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.telu.teluspace.databinding.ActivityBookingBinding
import java.util.Calendar

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding
    private var gedungId: Int = 0

    private val mGedung: BookingViewModel by lazy {
        ViewModelProvider(this)[BookingViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()

        binding.pilihanGedung.setOnItemClickListener { parent, _, position, _ ->
            val selectedGedung = parent.getItemAtPosition(position) as String

            // Ambil id_gedung berdasarkan gedung yang dipilih
            gedungId = mGedung.listGedung.value?.get(position)?.id ?: 0
            Toast.makeText(this@BookingActivity, "Gedung terpilih: $selectedGedung", Toast.LENGTH_SHORT).show()

            // Setel ruangan berdasarkan gedung yang dipilih
            mGedung.setRuangan(gedungId)
        }

        binding.edtTanggal.setOnClickListener {
            setDate()
        }
        binding.edtStartTime.setOnClickListener {
            setStartTime()
        }
        binding.edtEndTime.setOnClickListener {
            setEndTime()
        }
        binding.btnSend.setOnClickListener {
            startActivity(Intent(this, BookingSuccessActivity::class.java))
            finish()
        }
    }

    private fun setView() {
        supportActionBar?.apply {
            val title = SpannableString("Tambah Booking Kelas")
            title.setSpan(
                RelativeSizeSpan(.8f),
                0,
                title.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )

            // Ubah warna teks menjadi hitam
            title.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0,
                title.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )

            setTitle(title)
            setDisplayHomeAsUpEnabled(true)
            setBackgroundDrawable(
                ContextCompat.getDrawable(
                    this@BookingActivity,
                    R.color.white
                )
            )
            setHomeAsUpIndicator(R.drawable.ic_black_arrow)
        }
    }

    override fun onResume() {
        super.onResume()

        mGedung.setGedung()

        mGedung.listGedung.observe(this@BookingActivity) { listGedung ->
            val autoCompleteTextView: AutoCompleteTextView = binding.pilihanGedung
            val gedungNames = listGedung.map { it.namaGedung } // Ambil hanya nama gedung
            val adapter = ArrayAdapter(this, R.layout.list_item, gedungNames)
            autoCompleteTextView.setAdapter(adapter)
        }

        mGedung.listRuangan.observe(this@BookingActivity) { listRuangan ->
            val autoCompleteTextView2: AutoCompleteTextView = binding.pilihanRuangan
            val ruanganNames = listRuangan.map { it.nomorRuangan } // Ambil hanya nama ruangan
            val adapter2 = ArrayAdapter(this, R.layout.list_item, ruanganNames)
            autoCompleteTextView2.setAdapter(adapter2)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                binding.edtTanggal.setText("$dayOfMonth/${monthOfYear + 1}/$year")
            },
            year,
            month,
            day
        )
        dpd.show()
    }

    @SuppressLint("SetTextI18n")
    private fun setStartTime() {
        val isSystem24Hour = android.text.format.DateFormat.is24HourFormat(this)
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(9)
            .setMinute(0)
            .setTitleText("Waktu Mulai")
            .build()
        picker.show(supportFragmentManager, "tag")

        picker.addOnPositiveButtonClickListener {
            val hourStart = String.format("%02d", picker.hour) // Tambahkan nol di depan jika angka satu digit
            val minuteStart = String.format("%02d", picker.minute) // Tambahkan nol di depan jika angka satu digit
            binding.edtStartTime.setText("$hourStart:$minuteStart")
            Toast.makeText(this, "$hourStart:$minuteStart", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setEndTime() {
        val isSystem24Hour = android.text.format.DateFormat.is24HourFormat(this)
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(9)
            .setMinute(0)
            .setTitleText("Waktu Selesai")
            .build()
        picker.show(supportFragmentManager, "tag")

        picker.addOnPositiveButtonClickListener {
            val hourEnd = String.format("%02d", picker.hour) // Tambahkan nol di depan jika angka satu digit
            val minuteEnd = String.format("%02d", picker.minute) // Tambahkan nol di depan jika angka satu digit
            binding.edtEndTime.setText("$hourEnd:$minuteEnd")
            Toast.makeText(this, "$hourEnd:$minuteEnd", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Memulai aktivitas utama (home activity)
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

