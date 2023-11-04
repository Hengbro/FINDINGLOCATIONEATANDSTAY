package com.inyonghengki.findinglocation.ui.categoryfasilitas.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.CategoryFasilitas
import com.inyonghengki.findinglocation.databinding.ItemSelectCategoryBinding
import com.inyonghengki.findinglocation.util.toUrlCategory
import com.inyongtisto.myhelper.extension.*

@SuppressLint("NotifyDataSetChanged")
class SelectCategoryFasilitasAdapter(
    val onClick: (item: CategoryFasilitas) -> Unit
) : RecyclerView.Adapter<SelectCategoryFasilitasAdapter.ViewHolder>() {

    private var data = ArrayList<CategoryFasilitas>()

    inner class ViewHolder(private val itemBinding: ItemSelectCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: CategoryFasilitas, position: Int) {
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

    fun addItems(items: List<CategoryFasilitas>) {
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

