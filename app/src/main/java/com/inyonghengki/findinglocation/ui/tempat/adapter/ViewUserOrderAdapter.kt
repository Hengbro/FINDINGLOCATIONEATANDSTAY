package com.inyonghengki.findinglocation.ui.tempat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangTempat
import com.inyonghengki.findinglocation.databinding.ItemKonfirmasiBayarBinding
import com.inyongtisto.myhelper.extension.toRupiah
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("NotifyDataSetChanged","SetTextI18n", "SimpleDateFormat")
class ViewUserOrderAdapter(
    private val onClick: (KeranjangTempat, pos: Int) -> Unit,
    private val onTwoClick: (KeranjangTempat) -> Unit
): RecyclerView.Adapter<ViewUserOrderAdapter.ViewHolder>(){

    private var data = ArrayList<KeranjangTempat>()

    inner class ViewHolder(private val itemBinding: ItemKonfirmasiBayarBinding): RecyclerView.ViewHolder(itemBinding.root){


        fun bind(item : KeranjangTempat, position: Int) {
            itemBinding.apply {
                tvNamauser.text = item.user?.name
                tvSumqty.text = item.sum_qty.toString()
                tvSumprice.text = item.sum_harga.toRupiah()
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
                    onTwoClick.invoke(item)
                }

                btnKonfir.setOnClickListener {
                    onClick.invoke(item, adapterPosition)
                }
            }
        }

    }

    fun removeAt(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItems(items : List<KeranjangTempat>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemKonfirmasiBayarBinding.inflate(
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

