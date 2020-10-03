package com.surendra.note_app.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.surendra.note_app.R
import com.surendra.note_app.data.Note
import com.surendra.note_app.data.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_notes.*
import kotlinx.coroutines.launch


class AddNotesFragment : BaseFragment() {

    private var note: Note? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_notes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
             note=AddNotesFragmentArgs.fromBundle(it).note
                etNoteTitle.setText(note?.title)
                etNoteText.setText(note?.note)
        }

        fbSave.setOnClickListener {
            val noteTitle=etNoteTitle.text.toString().trim()
            val noteText=etNoteText.text.toString().trim()

            if (noteTitle.isEmpty()){
                etNoteTitle.error="Title required"
                etNoteTitle.requestFocus()
                return@setOnClickListener
            }

            if (noteText.isEmpty()){
                etNoteText.error="Note required"
                etNoteText.requestFocus()
                return@setOnClickListener
            }

            launch {
                context?.let {
                    val mNote=Note(noteTitle,noteText)
                    if (note==null){
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("Note Saved")
                    }
                    else{
                        mNote.id=note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note Updated")
                    }


                    val action=AddNotesFragmentDirections.actionAddNotesFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteNote->if (note !=null) deleteNote()
            else->context?.toast("Cannot deleted")
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote(){
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Are You Sure")
            setMessage("You cannot undo the changes")
            setPositiveButton("Yes"){_,_ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    NoteDatabase(requireContext()).getNoteDao().deleteNote(note!!)
                    val action=AddNotesFragmentDirections.actionAddNotesFragmentToHomeFragment()
                    findNavController().navigate(action)

                }
            }
            setNegativeButton("No"){_,_ ->

            }
        }.create().show()
    }


}