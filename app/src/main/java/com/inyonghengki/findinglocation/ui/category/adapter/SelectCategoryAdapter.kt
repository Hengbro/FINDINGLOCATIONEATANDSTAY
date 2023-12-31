package com.inyonghengki.findinglocation.ui.category.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.Category
import com.inyonghengki.findinglocation.databinding.ItemSelectCategoryBinding
import com.inyonghengki.findinglocation.util.toUrlCategory
import com.inyongtisto.myhelper.extension.setImagePicasso

@SuppressLint("NotifyDataSetChanged", "SetTextI18n")
class SelectCategoryAdapter(
    val onClick: (item: Category) -> Unit
) : RecyclerView.Adapter<SelectCategoryAdapter.ViewHolder>() {

    private var data = ArrayList<Category>()

    inner class ViewHolder(private val itemBinding: ItemSelectCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Category, position: Int) {
            itemBinding.apply {
                val context = root.context
                tvName.text = item.name
                val imageProduct = item.image
                imageView.setImagePicasso(imageProduct.toUrlCategory())

                lyMain.setOnClickListener {
                    onClick.invoke(item)
                }
            }
        }
    }

    fun removeAt(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItems(items: List<Category>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSelectCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

