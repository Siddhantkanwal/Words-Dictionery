package com.example.words

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class WordAdapter(private val letterId:String,context: Context):RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private val filteredWords: List<String>
    init {
        val words = context.resources.getStringArray(R.array.words).toList()
        filteredWords=words
            .filter { it.startsWith(letterId,ignoreCase = true) }
            .shuffled()
            .take(5)
            .sorted()
    }


    class WordViewHolder(val view:View):RecyclerView.ViewHolder(view) {
        val button = view.findViewById<Button>(R.id.button_item)

    }

//    @android.support.annotation.RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        // Setup custom accessibility delegate to set the text read
        layout.accessibilityDelegate = LetterAdapter

        return WordViewHolder(layout)    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = filteredWords[position]
        val context = holder.view.context
        holder.button.text = item
        holder.button.setOnClickListener(){
            val queryUrl: Uri = Uri.parse("${DetailActivity.SEARCH_PREFIX}${item}")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return filteredWords.size
    }
}