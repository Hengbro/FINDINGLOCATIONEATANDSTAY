package com.inyonghengki.findinglocation.ui.tempat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangTempat
import com.inyonghengki.findinglocation.databinding.ItemCartstoreBinding
import com.inyongtisto.myhelper.extension.toRupiah
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("NotifyDataSetChanged","SetTextI18n", "SimpleDateFormat")
class ViewReallyBuyByPlaceAdapter(
    val onClick:(item: KeranjangTempat) -> Unit
): RecyclerView.Adapter<ViewReallyBuyByPlaceAdapter.ViewHolder>(){

    private var data = ArrayList<KeranjangTempat>()

    inner class ViewHolder(private val itemBinding: ItemCartstoreBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : KeranjangTempat, position: Int) {
            itemBinding.apply {
                tvNamatempat.text = item.user?.name
                tvTotalqty.text = item.sum_qty.toString()
                tvTotalprice.text = item.sum_harga.toRupiah()
                tvStatus.text = item.status

                val dateTimeString = item.lastInsert
                val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                try {
                    val date: Date? = dateTimeFormat.parse(dateTimeString.toString())
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd")

                    if (date != null) {
                        tvTgl.text = dateFormat.format(date)
                    } else {
                        // Handle null date
                    }
                } catch (e: ParseException) {
                    // Handle parsing exception
                }

                layout.setOnClickListener {
                    onClick.invoke(item)
                }
            }
        }

    }

    fun addItems(items : List<KeranjangTempat>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCartstoreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

