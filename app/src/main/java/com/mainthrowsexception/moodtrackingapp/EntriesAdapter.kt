package com.mainthrowsexception.moodtrackingapp

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class EntriesAdapter(
    private val entries: List<Entry>
) : RecyclerView.Adapter<EntriesAdapter.EntryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.entry, parent, false)
        return EntryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        holder.bind(entries[position])
    }


    class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivEmoji: ImageView = itemView.findViewById(R.id.entry__iv_emoji)
        private val tvCreationTime: TextView = itemView.findViewById(R.id.entry__tv_creation_time)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.entry__layout)
        private val cgTags: ChipGroup = itemView.findViewById(R.id.entry__cg_tags)

        @SuppressLint("ResourceAsColor")
        fun bind(entry: Entry) {
            when (entry.mood) {
                1 -> {
                    ivEmoji.setImageResource(R.drawable.ic_very_sad_emoji)
                    layout.background =
                        ContextCompat.getDrawable(itemView.context, R.drawable.mood_very_bad_bg);
                }
                2 -> {
                    ivEmoji.setImageResource(R.drawable.ic_sad_emoji)
                    layout.background =
                        ContextCompat.getDrawable(itemView.context, R.drawable.mood_bad_bg);
                }
                3 -> {
                    ivEmoji.setImageResource(R.drawable.ic_neutral_emoji)
                    layout.setBackgroundColor(R.color.grey)
                }
                4 -> {
                    ivEmoji.setImageResource(R.drawable.ic_happy_emoji)
                    layout.background =
                        ContextCompat.getDrawable(itemView.context, R.drawable.mood_good_bg);
                }
                5 -> {
                    ivEmoji.setImageResource(R.drawable.ic_very_happy_emoji)
                    layout.background =
                        ContextCompat.getDrawable(itemView.context, R.drawable.mood_very_good_bg);
                }
            }

            for (tag in entry.tags) {
                val chip: Chip = Chip(itemView.context)
                chip.text = tag
                chip.setTextAppearanceResource(R.style.tag_text)
                chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.dark_grey))
                cgTags.addView(chip)
            }

            tvCreationTime.text = android.text.format.DateFormat.format("hh:mm", entry.time)
        }
    }
}