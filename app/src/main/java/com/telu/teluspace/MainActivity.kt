package com.telu.teluspace

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.telu.teluspace.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListBookAdapter

    private val userPreference by lazy {
        UserPreference.getInstance(dataStore)
    }

    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        adapter = ListBookAdapter()
        binding.rvBooked.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvBooked.setHasFixedSize(true)
        binding.rvBooked.adapter = adapter

        binding.booking.setOnClickListener {
            val intent = Intent(this@MainActivity, BookingActivity::class.java)
            startActivity(intent)
        }
        binding.imgLogout.setOnClickListener {
            lifecycleScope.launch {
                userPreference.logout()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed()
        } else {
            backPressedOnce = true
            Toast.makeText(this, "Tekan tombol back sekali lagi jika ingin keluar", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                backPressedOnce = false
            }, 2000)
        }
    }
}
