package com.inyonghengki.findinglocation.ui.menufasilitas.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.R
import com.inyonghengki.findinglocation.databinding.ItemUploadtempatBinding
import com.inyonghengki.findinglocation.util.toUrlFasilitas
import com.inyonghengki.findinglocation.util.toUrlProduct
import com.inyongtisto.myhelper.extension.logs
import com.inyongtisto.myhelper.extension.setImagePicasso
import com.inyongtisto.myhelper.extension.visible

@SuppressLint("NotifyDataSetChanged")
class AddImagesFaslitasAdapter (
    var onAddImage: () -> Unit,
    var onDeleteImage:(Int)-> Unit):
    RecyclerView.Adapter<AddImagesFaslitasAdapter.ViewHolder>(){

    private var data = ArrayList<String>()

    inner class ViewHolder(private val itemBinding: ItemUploadtempatBinding):
        RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item : String, position: Int){
            itemBinding.apply {

                logs("Gambar:$item - ${item.toUrlFasilitas()}")
                if (item.isNotEmpty()) {
                    btnAddFoto.setImagePicasso(item.toUrlFasilitas())
                } else btnAddFoto.setImageResource(R.drawable.addimage)

                btnClose.visible(item.isNotEmpty())
                btnAddFoto.setOnClickListener {
                    onAddImage.invoke()
                }
                btnClose.setOnClickListener {
                    onDeleteImage.invoke(adapterPosition)
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

