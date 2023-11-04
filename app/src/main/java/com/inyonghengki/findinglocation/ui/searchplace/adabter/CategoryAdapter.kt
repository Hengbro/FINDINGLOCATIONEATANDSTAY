package com.inyonghengki.findinglocation.ui.searchplace.adabter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.Category
import com.inyonghengki.findinglocation.databinding.ItemCategorySearchBinding
import com.inyonghengki.findinglocation.ui.searchplace.ResultHasilActivity
import com.inyonghengki.findinglocation.util.toUrlCategory
import com.inyongtisto.myhelper.extension.setImagePicasso

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    private var data = ArrayList<Category>()

    inner class ViewHolder(private val itemBinding: ItemCategorySearchBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item : Category, position: Int){
            itemBinding.apply {
                tvnmCategory.text = item.name
                imageView.setImagePicasso(item.image.toUrlCategory())

                val context = root.context
                layout.setOnClickListener {
                    val message = tvnmCategory.text.toString()
                    val intent = Intent(context, ResultHasilActivity::class.java)
                    intent.apply {
                        putExtra("themessage", message)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: List<Category>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCategorySearchBinding.inflate(
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

