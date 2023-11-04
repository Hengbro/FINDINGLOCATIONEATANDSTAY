package com.inyonghengki.findinglocation.ui.categoryfasilitas.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.CategoryFasilitas
import com.inyonghengki.findinglocation.databinding.ItemCategoryProductAdminBinding
import com.inyonghengki.findinglocation.util.toUrlCategoryFas
import com.inyongtisto.myhelper.extension.setImagePicasso


@SuppressLint("NotifyDataSetChanged", "SetTextI18n")
class CategoryFasilitasAdminAdapter (

    val onClick:(item: CategoryFasilitas) -> Unit,
    val onDelete:(item: CategoryFasilitas, pos: Int) -> Unit):
    RecyclerView.Adapter<CategoryFasilitasAdminAdapter.ViewHolder>(){

    private var data = ArrayList<CategoryFasilitas>()

    inner class ViewHolder(private val itemBinding: ItemCategoryProductAdminBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : CategoryFasilitas, position: Int){

            itemBinding.apply {

                tvName.text = item.name
                val imageCategory = item.image

                imageView.setImagePicasso(imageCategory.toUrlCategoryFas())
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

    fun addItems(items : List<CategoryFasilitas>){
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

