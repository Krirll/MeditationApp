package com.learn.meditationapp.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learn.meditationapp.API.Quote
import com.learn.meditationapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

class QuoteAdapter (private val list : List<Quote>) : RecyclerView.Adapter<QuoteAdapter.MyViewHolder>() {

    class MyViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.titleQuote)
        var description: TextView = itemView.findViewById(R.id.descQuote)
        var image: ImageView = itemView.findViewById(R.id.imageQuote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.quote_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.description.text = list[position].description
        CoroutineScope(Dispatchers.Main).launch {
            var image : Drawable
            withContext(Dispatchers.IO) {
                image =
                    Drawable.createFromStream(
                        URL(list[position].image).content as InputStream, "src"
                    )
            }
            holder.image.setImageDrawable(image)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}