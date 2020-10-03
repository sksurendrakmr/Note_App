package com.surendra.note_app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.surendra.note_app.R
import com.surendra.note_app.data.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_view_notes.setHasFixedSize(true)
        rv_view_notes.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        viewLifecycleOwner.lifecycleScope.launch {
                val notes=NoteDatabase(requireContext()).getNoteDao().getAllNote()
                rv_view_notes.adapter=NoteAdapter(notes)


        }
        fvAdd.setOnClickListener {
            val action=HomeFragmentDirections.actionHomeFragmentToAddNotesFragment()
            findNavController().navigate(action)
        }
    }


}
