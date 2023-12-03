package com.omen.chitrakavya.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.omen.chitrakavya.R
import com.omen.chitrakavya.models.PoemDisplayItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PoemAdapter(private var poemList: List<PoemDisplayItem>) :
    RecyclerView.Adapter<PoemAdapter.PoemViewHolder>() {

    // Inner ViewHolder class to hold item views
    class PoemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)
        val textViewLines: TextView = itemView.findViewById(R.id.textViewLines)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_poem, parent, false)
        return PoemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PoemViewHolder, position: Int) {
        val currentPoem = poemList[position]
        holder.textViewTitle.text = currentPoem.title
        holder.textViewAuthor.text = currentPoem.author

        //  Typing animation for the poem lines
        animateText(currentPoem.lines.joinToString("\n"), holder.textViewLines)
    }

    override fun getItemCount(): Int {
        return poemList.size
    }

    // Update data
    fun setData(newList: List<PoemDisplayItem>) {
        poemList = newList
        notifyDataSetChanged()
    }

    // Function to animate text
    private fun animateText(text: String, textView: TextView) {
        CoroutineScope(Dispatchers.Main).launch {
            for (i in text.indices) {
                delay(10)
                textView.text = text.substring(0, i + 1)
            }
        }
    }
}
