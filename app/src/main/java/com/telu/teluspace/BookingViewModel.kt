package com.telu.teluspace

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.*

class BookingViewModel : ViewModel() {
    private val _listGedung = MutableLiveData<ArrayList<GedungItem>>()
    val listGedung: LiveData<ArrayList<GedungItem>> = _listGedung

    private val _listRuangan = MutableLiveData<ArrayList<RuanganItem>>()
    val listRuangan: LiveData<ArrayList<RuanganItem>> = _listRuangan

    private val _gedungId = MutableLiveData<Int>()
    val gedungId: LiveData<Int> = _gedungId

    fun setGedung() {
        ApiConfig.getApiServices().getGedung()
            .enqueue(object : Callback<GedungResponse> {
                override fun onResponse(
                    call: Call<GedungResponse>,
                    response: Response<GedungResponse>
                ) {
                    if (response.isSuccessful) {
                        val gedungItems = response.body()?.data
                        _listGedung.postValue(gedungItems)
                        if (gedungItems?.isNotEmpty() == true) {
                            // Setel nilai gedungId jika belum diinisialisasi
                            if (_gedungId.value == null) {
                                _gedungId.postValue(gedungItems[0].id)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<GedungResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun setRuangan(idGedung: Int) {
        ApiConfig.getApiServices().getRuanganByGedung(idGedung)
            .enqueue(object : Callback<RuanganByGedungResponse> {
                override fun onResponse(
                    call: Call<RuanganByGedungResponse>,
                    response: Response<RuanganByGedungResponse>
                ) {
                    if (response.isSuccessful) {
                        _listRuangan.postValue(response.body()?.data)
                    }
                }

                override fun onFailure(call: Call<RuanganByGedungResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun updateGedungId(idGedung: Int) {
        _gedungId.postValue(idGedung)
    }
}
