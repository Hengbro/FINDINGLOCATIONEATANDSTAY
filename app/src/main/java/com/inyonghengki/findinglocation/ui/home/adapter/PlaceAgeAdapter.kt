package com.inyonghengki.findinglocation.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.Pengunjung
import com.inyonghengki.findinglocation.databinding.ItemPlaceAgeBinding
import com.inyonghengki.findinglocation.util.toUrlTempat
import com.inyongtisto.myhelper.extension.setImagePicasso

@SuppressLint("NotifyDataSetChanged","SetTextI18n")
class PlaceAgeAdapter (

    val onClick:(item: Pengunjung) -> Unit

) : RecyclerView.Adapter<PlaceAgeAdapter.ViewHolder>(){

    private var data = ArrayList<Pengunjung>()

    inner class ViewHolder(private val itemBinding: ItemPlaceAgeBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : Pengunjung, position: Int){
            itemBinding.apply {
                tvnmProduk.text = item.place?.nameTempat
                tvReting.text = "" +item.place?.avgReview+"| Pengunjung: " +item.place?.pengunjung.toString()

                val splitImages = item.place?.imageTempat?.split("|")
                val imageTempat = if (splitImages.isNullOrEmpty()){
                    item.place?.imageTempat?:""
                }else {
                    splitImages[0]
                }

                imgProduk.setImagePicasso(imageTempat.toUrlTempat())

                layoutbaru.setOnClickListener{
                    onClick.invoke(item)
                }

            }
        }

    }

    fun addItems(items : List<Pengunjung>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPlaceAgeBinding.inflate(
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

