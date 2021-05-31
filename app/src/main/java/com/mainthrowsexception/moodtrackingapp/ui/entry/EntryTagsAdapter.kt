package com.mainthrowsexception.moodtrackingapp.ui.entry

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mainthrowsexception.moodtrackingapp.R
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

class EntryTagsAdapter(
    val entryFragment: EntryFragment,
    val items: MutableList<String>
) : RecyclerView.Adapter<EntryTagsAdapter.TagViewHolder>() {

    init {
        items.add(0, PLUS_SYM)
    }

    fun getTagNames(): MutableList<String> {
        return items.stream()
            .skip(1)
            .collect(Collectors.toList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val tagView = LayoutInflater.from(parent.context).inflate(R.layout.mood_tag, parent, false)
        return TagViewHolder(tagView)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bindData(items[position])
        holder.setOnClickListener(this)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun onClick(holder: TagViewHolder) {
        when (holder.adapterPosition) {
            0 -> entryFragment.showAddingTagSection()
            else -> onClickTag(holder)
        }
    }

    private var activatedDeleteBtnHolder: TagViewHolder? = null

    private fun onClickTag(holder: TagViewHolder) {
        Log.d("EntryTagsAdapter", "Click on tag")
        activatedDeleteBtnHolder?.let {
            activatedDeleteBtnHolder!!.hideDeleteBtn()
            if (activatedDeleteBtnHolder!!.adapterPosition == holder.adapterPosition) {
                removeItemAt(holder.adapterPosition)
            }
            activatedDeleteBtnHolder = null
            return
        }
        holder.showDeleteBtn()
        activatedDeleteBtnHolder = holder
    }

    private fun removeItemAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    fun addTagName(tagName: String) {
        items.add(INSERT_TAG_POSITION, tagName)
        notifyItemChanged(INSERT_TAG_POSITION)
        notifyItemRangeChanged(INSERT_TAG_POSITION, items.size)
    }

    inner class TagViewHolder(tagView: View) : RecyclerView.ViewHolder(tagView) {
        private val tvTagText: TextView = tagView.findViewById(R.id.rv_item__tag_tv)
        private val ivTagDeleteBtn: ImageView = tagView.findViewById(R.id.rv_item__delete_tag_iv)

        fun setOnClickListener(adapter: EntryTagsAdapter) {
            tvTagText.setOnClickListener { adapter.onClick(this) }
            ivTagDeleteBtn.setOnClickListener { adapter.onClick(this) }
        }

        fun bindData(tagName: String) {
            tvTagText.text = tagName
        }

        fun showDeleteBtn() {
            tvTagText.alpha = 0f
            ivTagDeleteBtn.visibility = View.VISIBLE

        }

        fun hideDeleteBtn() {
            ivTagDeleteBtn.visibility = View.GONE
            tvTagText.alpha = 0.7f
        }
    }

    companion object {
        const val PLUS_SYM = "+"
        const val INSERT_TAG_POSITION = 1
        const val ADD_TAG_BTN_POSITION = 0

    }
}
