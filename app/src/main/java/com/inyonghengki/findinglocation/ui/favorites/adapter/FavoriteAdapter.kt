package com.inyonghengki.findinglocation.ui.favorites.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.Favorite
import com.inyonghengki.findinglocation.databinding.ItemCartFavoriteBinding
import com.inyonghengki.findinglocation.util.toUrlTempat
import com.inyongtisto.myhelper.extension.setImagePicasso

@SuppressLint("NotifyDataSetChanged","SetTextI18n")
class FavoriteAdapter(

    val onClick:(item: Favorite) -> Unit,
    val onDelete: (item: Favorite, pos: Int) -> Unit

): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    private var data = ArrayList<Favorite>()

    inner class ViewHolder(private val itemBinding: ItemCartFavoriteBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : Favorite, position: Int){
            itemBinding.apply {
                tvnmProduk.text = item.place?.nameTempat
                tvKategori.text = item.place?.kategori ?: "Lainnya"
                tvReting.text = "" +item.place?.avgReview +"| Pengunjung: " +item.place?.pengunjung.toString()

                val splitImages = item.place?.imageTempat?.split("|")
                val imageTempat = if (splitImages.isNullOrEmpty()){
                    item.place?.imageTempat?:""
                }else {
                    splitImages[0]
                }

                imgProduk.setImagePicasso(imageTempat.toUrlTempat())

                val context = root.context
                layout.setOnClickListener {
                    onClick.invoke(item)
                }

                btnUnfav.setOnClickListener {
                    onDelete.invoke(item, adapterPosition)
                }
            }
        }

    }

    fun removeAt(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItems(items : List<Favorite>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCartFavoriteBinding.inflate(
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

