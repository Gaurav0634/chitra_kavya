package com.omen.chitrakavya.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omen.chitrakavya.PoemApplication
import com.omen.chitrakavya.databinding.FragmentHomeBinding
import com.omen.chitrakavya.repository.PoemRepository
import com.omen.chitrakavya.adapters.PoemAdapter
import com.omen.chitrakavya.models.PoemDisplayItem
import com.omen.chitrakavya.viewmodels.MainViewModel
import com.omen.chitrakavya.viewmodels.MainViewModelFactory
class HomeFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var poemRepository: PoemRepository
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        poemRepository = (requireActivity().application as PoemApplication).poemRepository

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(poemRepository))
            .get(MainViewModel::class.java)

        val recyclerView: RecyclerView = binding.recyclerViewPoems
        val poemAdapter = PoemAdapter(ArrayList()) // Pass an empty list initially

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = poemAdapter
        }

        mainViewModel.poems.observe(viewLifecycleOwner, Observer { poemList ->
            poemList?.let {
                // Convert PoemListItem objects to PoemDisplayItem objects
                val displayList = poemList.map { poemListItem ->
                    PoemDisplayItem(
                        poemListItem.title,
                        poemListItem.author,
                        poemListItem.lines
                    )
                }
                poemAdapter.setData(displayList) // Update the adapter with the converted list
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
