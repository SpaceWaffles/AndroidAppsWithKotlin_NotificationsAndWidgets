package com.jwhh.notekeeper

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_note.view.*

class NoteRecyclerAdapter(val context: Context) : RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_note, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return DataManager.notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = DataManager.notes[position]
        holder.textCourse.text = note.course?.title
        holder.textTitle.text = note.title
        holder.currentPosition = position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textCourse: TextView = itemView.findViewById<TextView>(R.id.textCourse)
        val textTitle: TextView = itemView.findViewById<TextView>(R.id.textTitle)
        var currentPosition = 0
        private var longPressed = false

        init {
            itemView.setOnClickListener {
                if (longPressed) {
                    longPressed = !longPressed
                    resetReminderButton()
                }
                val intent = Intent(context, NoteActivity::class.java)
                intent.putExtra(NOTE_POSITION, currentPosition)
                context.startActivity(intent)
            }

            itemView.reminderButton.setOnClickListener {
                if (longPressed) {
                    longPressed = !longPressed
                    resetReminderButton()
                    NoteReminderNotification.notify(itemView.context,
                        "Reminder",
                        itemView.context.getString(R.string.reminder_body, textTitle.text),
                        currentPosition)
                }
            }

            itemView.setOnLongClickListener {
                longPressed = !longPressed
                if (longPressed) {
                    showReminderButton()
                } else {
                    resetReminderButton()
                }
                true
            }
        }

        private fun showReminderButton() {
            val animation = ObjectAnimator.ofFloat(itemView.clipboard, "rotationY", 0f, 90f)
            animation.duration = 150
            animation.start()

            val animation3 = ObjectAnimator.ofFloat(itemView.reminderButton, "rotationY", 90f, 0f)
            animation3.startDelay = 150
            animation3.duration = 150
            animation3.start()
        }

        private fun resetReminderButton() {
            val animation = ObjectAnimator.ofFloat(itemView.reminderButton, "rotationY", 0f, 90f)
            animation.duration = 150
            animation.start()

            val animation3 = ObjectAnimator.ofFloat(itemView.clipboard, "rotationY", 90f, 0f)
            animation3.startDelay = 150
            animation3.duration = 150
            animation3.start()
        }
    }
}