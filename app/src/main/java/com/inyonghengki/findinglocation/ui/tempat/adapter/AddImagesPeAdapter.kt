package com.inyonghengki.findinglocation.ui.tempat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.R
import com.inyonghengki.findinglocation.databinding.ItemUploadtempatBinding
import com.inyonghengki.findinglocation.util.toUrlPemilik
import com.inyonghengki.findinglocation.util.toUrlProduct
import com.inyonghengki.findinglocation.util.toUrlTempat
import com.inyongtisto.myhelper.extension.logs
import com.inyongtisto.myhelper.extension.setImagePicasso
import com.inyongtisto.myhelper.extension.visible

@SuppressLint("NotifyDataSetChanged")
class AddImagesPeAdapter (
    var onAddImagePe: () -> Unit,
    var onDeleteImagePe:(Int)-> Unit):
    RecyclerView.Adapter<AddImagesPeAdapter.ViewHolder>(){

    private var data = ArrayList<String>()

    inner class ViewHolder(private val itemBinding: ItemUploadtempatBinding):
        RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item : String, position: Int){
            itemBinding.apply {

                logs("Gambar:$item - ${item.toUrlPemilik()}")
                if (item.isNotEmpty()) {
                    btnAddFoto.setImagePicasso(item.toUrlPemilik())
                } else btnAddFoto.setImageResource(R.drawable.addimage)

                btnClose.visible(item.isNotEmpty())
                btnAddFoto.setOnClickListener {
                    onAddImagePe.invoke()
                }
                btnClose.setOnClickListener {
                    onDeleteImagePe.invoke(adapterPosition)
                }
            }
        }

    }


    fun removeAt(index: Int){
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItems(items : List<String>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUploadtempatBinding.inflate(
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

