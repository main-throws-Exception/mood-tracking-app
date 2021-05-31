package com.mainthrowsexception.moodtrackingapp.ui.entry

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.EntryContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.EntryPresenter
import com.mainthrowsexception.moodtrackingapp.ui.currentday.CurrentDayFragment
import com.mainthrowsexception.moodtrackingapp.ui.entry.slider.EntrySlider
import java.text.SimpleDateFormat

class EntryFragment() : BaseFragment(), EntryContract.View, View.OnClickListener {

    private lateinit var llContent: LinearLayout
    private lateinit var ivEmoji: ImageView
    private lateinit var tvTime: TextView
    private lateinit var tvState: TextView
    private lateinit var ivSaveChangesInMoodBtnBg: ImageView
    private lateinit var ivMoodStateBg: ImageView

    private lateinit var rvTags: RecyclerView

    private lateinit var llAddingTagTitle: LinearLayout
    private lateinit var etAddingTag: EditText
    private lateinit var ivAddingTagBtnBg: ImageView
    private lateinit var btnAddingTag: AppCompatButton

    private lateinit var etNote: EditText

    private var presenter = EntryPresenter(this)
    private var entry = Entry(mood = 0)
    private lateinit var slider: EntrySlider

    constructor(entry: Entry) : this() {
        this.entry = entry
        presenter = EntryPresenter(this, entry)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        llContent = rootView.findViewById(R.id.fragment_entry__content)

        navigationPresenter.startLoading()
        llContent.visibility = View.GONE


        initMoodStateSection()
        initSelectedTagsSection()
        initAddingTagSection()
        initNoteSection()

        initButtonListeners()

        presenter.bindView()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_entry
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mood_state__save_changes_btn -> saveChanges()
            R.id.fragment_entry__add_tag_btn -> addTagToSelectedSection()
            R.id.fragment_entry__save_changes_btn -> saveChanges()
        }
    }

    private fun initButtonListeners() {

        rootView.findViewById<AppCompatButton>(R.id.mood_state__save_changes_btn)
            .setOnClickListener(this)
        btnAddingTag.setOnClickListener(this)
        rootView.findViewById<AppCompatButton>(R.id.fragment_entry__save_changes_btn)
            .setOnClickListener(this)
    }

    private fun initMoodStateSection() {
        val clMoodStateSection: ConstraintLayout =
            rootView.findViewById(R.id.fragment_entry__mood_state_cl)
        ivEmoji = clMoodStateSection.findViewById(R.id.mood_state__emoji_image)
        tvTime = clMoodStateSection.findViewById(R.id.mood_state__time_tv)
        tvState = clMoodStateSection.findViewById(R.id.mood_state__tv)
        ivMoodStateBg = clMoodStateSection.findViewById(R.id.mood_state__bg)
        ivSaveChangesInMoodBtnBg = clMoodStateSection.findViewById(R.id.mood_state__save_changes_iv)
    }

    private fun initSelectedTagsSection() {
        rvTags = rootView.findViewById(R.id.fragment_entry__tags_rv)
    }

    private fun initAddingTagSection() {
        llAddingTagTitle = rootView.findViewById(R.id.fragment_entry__add_tag_title_ll)
        etAddingTag = rootView.findViewById(R.id.fragment_entry__adding_tag_et)
        ivAddingTagBtnBg = rootView.findViewById(R.id.fragment_entry__add_tag_btn_bg_iv)
        btnAddingTag = rootView.findViewById(R.id.fragment_entry__add_tag_btn)
    }

    private fun initNoteSection() {
        etNote = rootView.findViewById(R.id.fragment_entry__note_et)
    }

    private fun saveChanges() {
        Log.d("EntryFragment", "Click on save button")
        saveData()
        showCurrentDay()
    }

    private fun addTagToSelectedSection() {
        Log.d("EntryFragment", "Click on add tag button")
        val adapter = (rvTags.adapter as EntryTagsAdapter)
        val newTagName = etAddingTag.text.toString()
        if (newTagName.isEmpty()) {
            hideAddingTagSection()
            return
        }
        if (newTagName.length > MAX_TAG_NAME_LENGTH) {
            Toast.makeText(requireContext(), R.string.tag_name_too_long, Toast.LENGTH_SHORT).show()
            return
        }
        val tags = adapter.getTagNames()
        if (tags.stream()
                .anyMatch { it == newTagName }) {
            Toast.makeText(requireContext(), R.string.tag_already_exists, Toast.LENGTH_SHORT).show()
            return
        }
        etAddingTag.setText("")
        adapter.addTagName(newTagName)
        hideAddingTagSection()
    }

    private fun showCurrentDay() {
        navigationPresenter.addFragment(CurrentDayFragment())
    }

    override fun saveData() {
        val mood = slider.getCurrentMoodValue()
        val tags = (rvTags.adapter as EntryTagsAdapter).getTagNames()
        val note = etNote.text.toString()
        presenter.saveChanges(
            Entry(
                uid = entry.uid,
                userId = entry.userId,
                tags = tags,
                note = note,
                mood = mood,
                created = entry.created
            )
        )
    }

    override fun bindData(entry: Entry) {
        this.entry = entry
        bindMoodStateSection()
        bindSliderSection()
        bindSelectedTagsSection()
        bindAddingTagSection()
        bindNoteSection()
        navigationPresenter.stopLoading()
        llContent.visibility = View.VISIBLE
    }

    private fun setTitleText(titleRes: Int, textRes: Int) {
        val tvTitle = rootView.findViewById<LinearLayout>(titleRes)
            .findViewById<TextView>(R.id.entry__title_tv)
        tvTitle.text = getString(textRes)
    }

    private fun bindSliderSection() {
        val moodStateRes = mutableListOf(
            R.string.terrible,
            R.string.bad,
            R.string.neutral,
            R.string.good,
            R.string.excellent,
        )
        val moosStateColorIds = mutableListOf(
            R.color.terrible,
            R.color.bad,
            R.color.neutral,
            R.color.good,
            R.color.excellent,
        )
        val sliderLayout: Slider = rootView.findViewById(R.id.fragment_entry__mood_slider)

        slider = EntrySlider(
            slider = sliderLayout,
            entryFragment = this,
            moodValue = entry.mood,
            moodStateColorIds = moosStateColorIds
        )
    }

    private fun bindNoteSection() {
        setTitleText(R.id.fragment_entry__note_title_ll, R.string.note)
        etNote.setText(entry.note)
    }

    private fun bindAddingTagSection() {
        setTitleText(R.id.fragment_entry__add_tag_title_ll, R.string.add_tag)
        hideAddingTagSection()
    }

    private fun hideAddingTagSection() {
        Log.d("EntryTagsAdapter", "Click on hide adding tag section")
        llAddingTagTitle.visibility = View.GONE
        etAddingTag.visibility = View.GONE
        ivAddingTagBtnBg.visibility = View.GONE
        btnAddingTag.visibility = View.GONE
    }

    fun showAddingTagSection() {
        Log.d("EntryTagsAdapter", "Click on show adding tag section")
        llAddingTagTitle.visibility = View.VISIBLE
        etAddingTag.visibility = View.VISIBLE
        ivAddingTagBtnBg.visibility = View.VISIBLE
        btnAddingTag.visibility = View.VISIBLE
    }

    private fun bindSelectedTagsSection() {
        setTitleText(R.id.fragment_entry__tag_title_ll, R.string.selected_tags)
        val tagAdapter = EntryTagsAdapter(this, entry.tags)
        rvTags.adapter = tagAdapter
        rvTags.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
    }

    private fun bindMoodStateSection() {
        val color = colorResToColor(getColorResByMood(entry.mood))
        updateMoodState(entry.mood, color)
        tvTime.text = SimpleDateFormat("hh:mm").format(entry.created)
    }

    private fun getColorResByMood(mood: Int): Int {
        return when (mood) {
            1 -> R.color.bad
            2 -> R.color.neutral
            3 -> R.color.good
            4 -> R.color.excellent
            else -> R.color.terrible
        }
    }

    private fun getEmojiResByMood(mood: Int): Int {
        return when (mood) {
            1 -> R.drawable.ic_sad_emoji
            2 -> R.drawable.ic_neutral_emoji
            3 -> R.drawable.ic_happy_emoji
            4 -> R.drawable.ic_very_happy_emoji
            else -> R.drawable.ic_very_sad_emoji
        }
    }

    private fun getStateResByMood(mood: Int): Int {
        return when (mood) {
            1 -> R.string.bad
            2 -> R.string.neutral
            3 -> R.string.good
            4 -> R.string.excellent
            else -> R.string.terrible
        }
    }

    fun updateMoodState(mood: Int, color: Int) {
        if (context == null) return
        ivEmoji.setImageResource(getEmojiResByMood(mood))
        tvState.text = getString(getStateResByMood(mood))

        val whiteColor = ContextCompat.getColor(requireContext(), R.color.white)
        ivMoodStateBg.background = GradientBackground(color, whiteColor)
        ivSaveChangesInMoodBtnBg.background = getOvalBg(color)
    }

    private fun colorResToColor(colorRes: Int): Int {
        if (context == null) return 0
        return ContextCompat.getColor(requireContext(), colorRes)
    }

    private fun getOvalBg(color: Int): ShapeDrawable {
        return ShapeDrawable(OvalShape()).apply {
            intrinsicHeight = 50
            intrinsicWidth = 50
            paint.color = color

        }
    }

    class GradientBackground(startColor: Int, endColor: Int) : GradientDrawable(
        Orientation.LEFT_RIGHT, intArrayOf(startColor, endColor)
    )

    companion object {
        const val MAX_TAG_NAME_LENGTH = 15
    }
}
