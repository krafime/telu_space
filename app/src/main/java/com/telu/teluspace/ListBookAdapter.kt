package com.telu.teluspace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telu.teluspace.databinding.BookedRowItemBinding

class ListBookAdapter : RecyclerView.Adapter<ListBookAdapter.ListViewHolder>() {

    inner class ListViewHolder(val binding: BookedRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListBookAdapter.ListViewHolder {
        val binding = BookedRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListBookAdapter.ListViewHolder, position: Int) {
        holder.binding.apply {
            headerRow.text = "Gedung TULT / Ruang 0610"
            live.text = "Sedang Berlangsung"
            pemesan.text = "Pihak Pemesan : Dzaky"
            keperluan.text = "Keperluan : Acara IUM"
            tvWaktu.text = "Waktu"
            tvJamSesi.text = "08.30 - 11.30"
            tvTanggal.text = "Tanggal"
            tanggal.text = "12 Mei 2023"
            tvRuangan.text = "Ruangan"
            ruangan.text = "TULT 0624"
        }
    }

    override fun getItemCount(): Int = 3
}