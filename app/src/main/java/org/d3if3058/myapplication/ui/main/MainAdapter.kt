package org.d3if3058.myapplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3058.myapplication.databinding.ListItemBinding
import org.d3if3058.myapplication.model.Resep
import org.d3if3058.myapplication.network.ResepApi
import org.d3if3058.myapplication.R

class MainAdapter(private val onItemClick: (Resep) -> Unit) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private val data = mutableListOf<Resep>()

    fun updateData(newData: List<Resep>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val resep = data[position]
                    onItemClick.invoke(resep)
                }
            }
        }

        fun bind(resep: Resep) {
            with(binding) {
                namaMakanan.text = resep.nama
                Glide.with(makanan.context)
                    .load(ResepApi.getResepUrl(resep.imageId))
                    .error(R.drawable.baseline_broken_image_24)
                    .into(makanan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}
