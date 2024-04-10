package com.example.subsmission2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListGadgetAdapter(private val listGadget: ArrayList<Gadget>) : RecyclerView.Adapter<ListGadgetAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
val view : View =LayoutInflater.from(parent.context).inflate(R.layout.item_row_gadget, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(name, description, secondDescription, photo) = listGadget[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .fitCenter()
            .into(holder.imgPhoto)
        holder.tvName.text = name
        holder.tvDescription.text = description
        holder.itemView.setOnClickListener {
        val intent = Intent(holder.itemView.context, DetailActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("description", description)
        intent.putExtra("photo", photo)
            intent.putExtra("secondDescription", secondDescription)
        holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listGadget.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }

}