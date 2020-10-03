package com.surendra.note_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.surendra.note_app.R
import com.surendra.note_app.data.Note
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.note_layout.view.*

class NoteAdapter(private val notes:List<Note>):RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.note_layout,parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note=notes.get(position)
        holder.view.tv_view_note_title.text=note.title
        holder.view.tv_view_note_text.text=note.note

        holder.view.setOnClickListener {
            val action=HomeFragmentDirections.actionHomeFragmentToAddNotesFragment()
            action.note=note
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NoteViewHolder( val view:View) :RecyclerView.ViewHolder(view){

    }
}