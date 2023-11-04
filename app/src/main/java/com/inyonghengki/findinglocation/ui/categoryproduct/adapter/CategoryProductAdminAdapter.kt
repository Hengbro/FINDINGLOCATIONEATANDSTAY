package com.inyonghengki.findinglocation.ui.categoryproduct.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.CategoryProduct
import com.inyonghengki.findinglocation.databinding.ItemCategoryProductAdminBinding
import com.inyonghengki.findinglocation.util.toUrlCategoryPro
import com.inyongtisto.myhelper.extension.setImagePicasso


@SuppressLint("NotifyDataSetChanged", "SetTextI18n")
class CategoryProductAdminAdapter (

    val onClick:(item: CategoryProduct) -> Unit,
    val onDelete:(item: CategoryProduct, pos: Int) -> Unit):
    RecyclerView.Adapter<CategoryProductAdminAdapter.ViewHolder>(){

    private var data = ArrayList<CategoryProduct>()

    inner class ViewHolder(private val itemBinding: ItemCategoryProductAdminBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : CategoryProduct, position: Int){

            itemBinding.apply {

                tvName.text = item.name
                val imageCategory = item.image
                imageView.setImagePicasso(imageCategory.toUrlCategoryPro())

                //val context = root.context

                btnDelete.setOnClickListener {
                    onDelete.invoke(item, adapterPosition)
                }

                btnEdit.setOnClickListener{
                    onClick.invoke(item)
                }

            }
        }

    }

    fun removeAt(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItems(items : List<CategoryProduct>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCategoryProductAdminBinding.inflate(
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

