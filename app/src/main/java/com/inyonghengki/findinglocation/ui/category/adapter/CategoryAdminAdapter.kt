package com.inyonghengki.findinglocation.ui.category.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.Category
import com.inyonghengki.findinglocation.databinding.ItemCategoryadminBinding
import com.inyonghengki.findinglocation.util.toUrlCategory
import com.inyongtisto.myhelper.extension.setImagePicasso


@SuppressLint("NotifyDataSetChanged", "SetTextI18n")
class CategoryAdminAdapter (

    val onClick:(item: Category) -> Unit,
    val onDelete:(item: Category, pos: Int) -> Unit):
    RecyclerView.Adapter<CategoryAdminAdapter.ViewHolder>(){

    private var data = ArrayList<Category>()

    inner class ViewHolder(private val itemBinding: ItemCategoryadminBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : Category, position: Int){

            itemBinding.apply {

                tvnmCategory.text = item.name
                tvDeskripsi.text = item.description

                val imageCategory = item.image

                imgCatgory.setImagePicasso(imageCategory.toUrlCategory())

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

    fun addItems(items : List<Category>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCategoryadminBinding.inflate(
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

