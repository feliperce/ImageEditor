package br.com.mobileti.imageeditor

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.text_color_item.view.*

class ColorSelectorAdapter(private val textColorList: Array<TextColor>) : RecyclerView.Adapter<ColorSelectorAdapter.ColorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorSelectorAdapter.ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.text_color_item, parent, false)
        return ColorViewHolder(view)
    }

    override fun getItemCount(): Int = textColorList.size


    override fun onBindViewHolder(holder: ColorSelectorAdapter.ColorViewHolder, position: Int) {
        val textColor = textColorList[position]
        holder.textColorLayout.isSelected = textColor.selected
        //holder.textColorLayout.setBackgroundResource(R.drawable.text_color_bg)
        //holder.textColorLayout.setBackgroundColor(textColor.color)
        holder.textColorLayout.background.setColorFilter(textColor.color, PorterDuff.Mode.SRC_ATOP)
    }

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textColorLayout: LinearLayout = itemView.textColorLayout
        val textColorView: View = itemView.textColorView
    }

}