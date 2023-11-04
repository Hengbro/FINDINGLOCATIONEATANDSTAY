package com.inyonghengki.findinglocation.ui.tempat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.Category
import com.inyonghengki.findinglocation.core.data.source.model.MenuFasilitas
import com.inyonghengki.findinglocation.core.data.source.model.Slider
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.databinding.ItemDetailfasilitasBinding
import com.inyonghengki.findinglocation.databinding.ItemNewtempatBinding
import com.inyonghengki.findinglocation.databinding.ItemSliderBinding
import com.inyonghengki.findinglocation.databinding.ItemTemprekomenBinding
import com.inyonghengki.findinglocation.ui.tempat.DetailTempatActivity
import com.inyonghengki.findinglocation.util.toUrlFasilitas
import com.inyonghengki.findinglocation.util.toUrlTempat
import com.inyongtisto.myhelper.extension.def
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setImagePicasso

@SuppressLint("NotifyDataSetChanged","SetTextI18n")
class FasilitasAdapter : RecyclerView.Adapter<FasilitasAdapter.ViewHolder>(){

    private var data = ArrayList<MenuFasilitas>()

    inner class ViewHolder(private val itemBinding: ItemDetailfasilitasBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : MenuFasilitas, position: Int){
            itemBinding.apply {
                tvnmProduk.text = item.name
                tvDeskripsi.text = item.description
                imgProduk.setImagePicasso(item.image?.toUrlFasilitas())
            }
        }

    }

    fun addItems(items : List<MenuFasilitas>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDetailfasilitasBinding.inflate(
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

