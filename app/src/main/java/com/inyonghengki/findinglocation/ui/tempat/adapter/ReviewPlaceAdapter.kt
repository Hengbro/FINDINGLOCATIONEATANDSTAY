package com.inyonghengki.findinglocation.ui.tempat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.Rating
import com.inyonghengki.findinglocation.databinding.ItemUlasanBinding


@SuppressLint("NotifyDataSetChanged", "SetTextI18n")
class ReviewPlaceAdapter :
    RecyclerView.Adapter<ReviewPlaceAdapter.ViewHolder>(){

    private var data = ArrayList<Rating>()

    inner class ViewHolder(private val itemBinding: ItemUlasanBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : Rating, position: Int){

            itemBinding.apply {
                tvName.text = item.user?.name
                tvSaran.text = item.review

            }
        }
    }

    fun addItems(items : List<Rating>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUlasanBinding.inflate(
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

