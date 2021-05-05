package com.mainthrowsexception.moodtrackingapp.ui.currentday

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import java.util.*

class EntriesAdapter(private val entries: MutableList<Entry>) : RecyclerView.Adapter<EntriesAdapter.EntryViewHolder>() {

//    наверное, это должно быть не тут...)))
    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val databaseRef = Firebase.database("https://goodmood-c69a1-default-rtdb.europe-west1.firebasedatabase.app/").reference
    val query = databaseRef.child("entries")
        .child(userId)
        .orderByChild("created")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.entry, parent, false)
        return EntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        holder.bind(entries[position])
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    inner class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivEmoji: ImageView = itemView.findViewById(R.id.entry__iv_emoji)
        private val tvCreationTime: TextView = itemView.findViewById(R.id.entry__tv_creation_time)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.entry__layout)
        private val cgTags: ChipGroup = itemView.findViewById(R.id.entry__cg_tags)

        private var btnEdit: AppCompatButton = itemView.findViewById(R.id.entry__edit_button)

        init {
            btnEdit.setOnClickListener { showPopup(it) }
        }

        private fun showPopup(view: View) {
            val selectedEntry = entries[adapterPosition]

            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.popup__edit -> {
                        Log.i("RECYCLER VIEW", "Edit clicked on " + selectedEntry.uid)
                        true
                    }
                    R.id.popup__delete -> {
                        Log.i("RECYCLER VIEW", "Delete clicked on " + selectedEntry.uid)
                        databaseRef.child("entries").child(userId).child(selectedEntry.uid).removeValue()
                        true
                    }
                    else -> true
                }
            }
            popupMenu.show()
        }

        @SuppressLint("ResourceAsColor")
        fun bind(entry: Entry) {
            when (entry.mood) {
                0 -> {
                    ivEmoji.setImageResource(R.drawable.ic_very_sad_emoji)
                    layout.background =
                        ContextCompat.getDrawable(itemView.context, R.drawable.mood_very_bad_bg)
                }
                1 -> {
                    ivEmoji.setImageResource(R.drawable.ic_sad_emoji)
                    layout.background =
                        ContextCompat.getDrawable(itemView.context, R.drawable.mood_bad_bg)
                }
                2 -> {
                    ivEmoji.setImageResource(R.drawable.ic_neutral_emoji)
                    layout.background =
                        ContextCompat.getDrawable(itemView.context, R.color.grey)
                }
                3 -> {
                    ivEmoji.setImageResource(R.drawable.ic_happy_emoji)
                    layout.background =
                        ContextCompat.getDrawable(itemView.context, R.drawable.mood_good_bg)
                }
                4 -> {
                    ivEmoji.setImageResource(R.drawable.ic_very_happy_emoji)
                    layout.background =
                        ContextCompat.getDrawable(itemView.context, R.drawable.mood_very_good_bg)
                }
            }

            cgTags.removeAllViews()

//            for (tag in entry.tags) {
//                val chip = Chip(itemView.context)
//                chip.text = tag
//                chip.setTextAppearanceResource(R.style.tag_text)
//                chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(itemView.context,
//                    R.color.dark_grey
//                ))
//                cgTags.addView(chip)
//            }

            tvCreationTime.text = android.text.format.DateFormat.format("hh:mm", Date(entry.created))
        }
    }
}
