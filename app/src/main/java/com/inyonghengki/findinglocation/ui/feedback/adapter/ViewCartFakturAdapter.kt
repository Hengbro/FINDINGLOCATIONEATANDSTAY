package com.inyonghengki.findinglocation.ui.feedback.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangProduct
import com.inyonghengki.findinglocation.databinding.ItemProductfacturBinding
import com.inyonghengki.findinglocation.util.toUrlProduct
import com.inyongtisto.myhelper.extension.setImagePicasso
import com.inyongtisto.myhelper.extension.toRupiah

@SuppressLint("NotifyDataSetChanged","SetTextI18n")
class ViewCartFakturAdapter(): RecyclerView.Adapter<ViewCartFakturAdapter.ViewHolder>(){

    private var data = ArrayList<KeranjangProduct>()

    inner class ViewHolder(private val itemBinding: ItemProductfacturBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : KeranjangProduct, position: Int){
            itemBinding.apply {
                tvNamaproduk.text = item.product?.name
                tvHarga.text = item.product?.price.toRupiah()
                tvQty.text = item.qty

                imgProduk.setImagePicasso(item.product?.image?.toUrlProduct())

            }
        }

    }

    fun removeAt(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItems(items : List<KeranjangProduct>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductfacturBinding.inflate(
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

