package com.depromeet.threedays.signup.palette

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.signup.R

class PaletteAdapter : RecyclerView.Adapter<PaletteAdapter.PaletteViewHolder>() {
    val list = mutableListOf<String>()

    inner class PaletteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewColor: View = itemView.findViewById(R.id.view_color)

        fun onBind(colorHex: String) {
            viewColor.setBackgroundColor(Color.parseColor(colorHex))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaletteViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_palette, parent, false)
        return PaletteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaletteViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(colors: List<String>) {
        list.clear()
        list.addAll(colors)
        notifyDataSetChanged()
    }
}