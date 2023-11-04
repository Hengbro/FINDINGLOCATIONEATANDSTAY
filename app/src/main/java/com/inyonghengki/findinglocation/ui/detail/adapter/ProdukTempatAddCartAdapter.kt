package com.inyonghengki.findinglocation.ui.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.MenuProduct
import com.inyonghengki.findinglocation.databinding.ItemOnedetailprodukBinding
import com.inyonghengki.findinglocation.util.toUrlProduct
import com.inyongtisto.myhelper.extension.setImagePicasso
import com.inyongtisto.myhelper.extension.toRupiah

@SuppressLint("NotifyDataSetChanged","SetTextI18n")
class ProdukTempatAddCartAdapter (
    val onClick:(item: MenuProduct) -> Unit
        ): RecyclerView.Adapter<ProdukTempatAddCartAdapter.ViewHolder>(){

    private var data = ArrayList<MenuProduct>()

    inner class ViewHolder(private val itemBinding: ItemOnedetailprodukBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : MenuProduct, position: Int){
            itemBinding.apply {
                tvnmProduk.text = item.name
                tvPrice.text = item.price.toRupiah()
                imgProduk.setImagePicasso(item.image?.toUrlProduct())

                btnAddtocart.setOnClickListener {
                    onClick.invoke(item)
                }
            }
        }

    }

    fun addItems(items : List<MenuProduct>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemOnedetailprodukBinding.inflate(
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

