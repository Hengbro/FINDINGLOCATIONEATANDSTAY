package com.inyonghengki.findinglocation.ui.adminpanel.tempat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.databinding.ItemConfirmationBinding
import com.inyonghengki.findinglocation.util.toUrlTempat
import com.inyongtisto.myhelper.extension.setImagePicasso


@SuppressLint("NotifyDataSetChanged", "SetTextI18n")
class MenuConfirmationAdapter (

    val onClick:(item: Tempat) -> Unit,
    val onClicktwo:(item: Tempat) -> Unit,
    val onDelete:(item: Tempat, pos: Int) -> Unit):
    RecyclerView.Adapter<MenuConfirmationAdapter.ViewHolder>(){

    private var data = ArrayList<Tempat>()

    inner class ViewHolder(private val itemBinding: ItemConfirmationBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : Tempat, position: Int) {

            itemBinding.apply {

                tvnmProduk.text = item.nameTempat
                tvKategori.text = item.category?.name ?: "Lainnya"
                tvKota.text = item.alamat?: "kota Medan"

                val splitImages = item.imageTempat?.split("|")
                val imageTempat = if (splitImages.isNullOrEmpty()) {
                    item.imageTempat ?: ""
                } else {
                    splitImages[0]
                }

                imgProduk.setImagePicasso(imageTempat.toUrlTempat())

                val context = root.context

                layout.setOnClickListener {
                    onClicktwo.invoke(item)
                }

                btnHapus.setOnClickListener {
                    val dialog = AlertDialog.Builder(context)
                    dialog.apply {
                        setTitle("Konfirmasi hapus tempat")
                        setMessage("Apakah anda yakin untuk menghapus tempat ini?")
                        setPositiveButton("HAPUS") { dialogInterface, i ->
                            onDelete.invoke(item, adapterPosition)
                            dialogInterface.dismiss()
                        }
                        setNegativeButton("BATAL") { dialogInterface, c ->
                            dialogInterface.dismiss()
                        }
                        dialog.show()
                    }

                }

                btnKonfir.setOnClickListener {
                    onClick.invoke(item)
                }

            }
        }
    }

    fun removeAt(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItems(items : List<Tempat>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemConfirmationBinding.inflate(
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

