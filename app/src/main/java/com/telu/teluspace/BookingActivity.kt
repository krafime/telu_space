package com.telu.teluspace

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.telu.teluspace.databinding.ActivityBookingBinding
import java.util.Calendar

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding
    private val gedung = listOf(
        "TULT",
        "KU1",
        "KU2",
        "KU3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtTanggal.setOnClickListener {
            setDate()
        }

        val autoCompleteTextView: AutoCompleteTextView = binding.pilihanGedung
        val adapter = ArrayAdapter(this, R.layout.list_item, gedung)

        autoCompleteTextView.setAdapter(adapter)

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = adapter.getItem(position)
            selectedItem?.let {
                binding.pilihanGedung.setText(it)
            }
            autoCompleteTextView.clearListSelection() // Clear the selected item
            autoCompleteTextView.dismissDropDown() // Dismiss the dropdown list
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
}

