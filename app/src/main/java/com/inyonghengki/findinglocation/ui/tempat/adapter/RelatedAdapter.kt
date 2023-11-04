package com.inyonghengki.findinglocation.ui.tempat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.databinding.ItemTempatterkaitBinding
import com.inyonghengki.findinglocation.util.toUrlTempat
import com.inyongtisto.myhelper.extension.setImagePicasso

@SuppressLint("NotifyDataSetChanged","SetTextI18n")
class RelatedAdapter (

    val onClick:(item: Tempat) -> Unit

) : RecyclerView.Adapter<RelatedAdapter.ViewHolder>(){

    private var data = ArrayList<Tempat>()

    inner class ViewHolder(private val itemBinding: ItemTempatterkaitBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : Tempat, position: Int){
            itemBinding.apply {
                tvnmProduk.text = item.nameTempat
                tvKategori.text = item.kategori?: "Lainnya"
                tvReting.text = "" +item.avgReview +"| Pengun.. : " +item.pengunjung.toString()
                tvKota.text = item.address?.alamat ?: "kota Medan"

                val splitImages = item.imageTempat?.split("|")
                val imageTempat = if (splitImages.isNullOrEmpty()){
                    item.imageTempat?:""
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

    fun addItems(items : List<Tempat>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTempatterkaitBinding.inflate(
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

