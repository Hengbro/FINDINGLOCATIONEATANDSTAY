package com.inyonghengki.findinglocation.ui.keranjangproduct.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangProduct
import com.inyonghengki.findinglocation.databinding.ItemCartproductBinding
import com.inyonghengki.findinglocation.util.toUrlProduct
import com.inyongtisto.myhelper.extension.setImagePicasso
import com.inyongtisto.myhelper.extension.toRupiah

@SuppressLint("NotifyDataSetChanged","SetTextI18n")
class ViewKeranjangPesananAdapter(
    val onDelete: (item: KeranjangProduct, pos: Int) -> Unit
): RecyclerView.Adapter<ViewKeranjangPesananAdapter.ViewHolder>(){

    private var data = ArrayList<KeranjangProduct>()

    inner class ViewHolder(private val itemBinding: ItemCartproductBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : KeranjangProduct, position: Int){
            itemBinding.apply {
                tvNamaproduk.text = item.product?.name
                tvHarga.text = item.product?.price.toRupiah()
                tvQty.text = item.qty

                val splitImages = item.product?.image?.split("|")
                val imageProduct = if (splitImages.isNullOrEmpty()){
                    item.product?.image?:""
                }else {
                    splitImages[0]
                }

                imgProduk.setImagePicasso(imageProduct.toUrlProduct())

                val context = root.context
                btnDelete.setOnClickListener {
                    onDelete.invoke(item, adapterPosition)
                }

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
        return ViewHolder(ItemCartproductBinding.inflate(
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

