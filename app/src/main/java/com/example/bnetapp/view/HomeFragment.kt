package com.example.bnetapp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bnetapp.R
import com.example.bnetapp.databinding.FragmentHomeBinding
import com.example.bnetapp.model.retrofit.drugs.DrugsItem
import com.example.bnetapp.viewModel.MainViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), DrugsRecyclerItem.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchEditText.visibility = View.GONE

        binding.topAppBarHome.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search_button -> {
                    binding.topAppBarHome.menu.findItem(R.id.search_button).isVisible = false
                    binding.topAppBarHome.menu.findItem(R.id.close_button).isVisible = true
                    binding.searchEditText.visibility = View.VISIBLE
                    binding.searchEditText.requestFocus()
                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.showSoftInput(binding.searchEditText, InputMethodManager.SHOW_IMPLICIT)
                    true
                }
                R.id.close_button -> {
                    binding.topAppBarHome.menu.findItem(R.id.search_button).isVisible = true
                    binding.topAppBarHome.menu.findItem(R.id.close_button).isVisible = false
                    binding.searchEditText.visibility = View.GONE
                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
                    true
                }
                else -> false
            }
        }

        val adapterList: MutableList<DrugsItem> = mutableListOf()
        val myAdapter = DrugsRecyclerItem(adapterList, requireContext(), this)

        binding.homeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.homeRecyclerView.adapter = myAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getDrugsCode().collect {
                    if (it != null) {
                        if (it == 100) {
                            Toast.makeText(context, "Oups! Nothing found",
                                Toast.LENGTH_SHORT).show()
                        } else if (it != 200) {
                            Log.w(tag, "Something went wrong")
                            Toast.makeText(context, "Oups! Something went wrong…\n" +
                                    "Check your internet connection", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getDrugs().collect {
                    if (it != null) {

                        adapterList.clear()
                        adapterList.addAll(it)

                        myAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        viewModel.updateDrugs("")

        binding.searchEditText.doOnTextChanged { text, start, before, count ->
            if (text != null) {
                viewModel.updateDrugs(text.toString())
            }
        }



    }

    override fun onItemClick(position: Int) {
        viewModel.setSingItemId(position)
        findNavController().navigate(R.id.action_homeFragment_to_singleCardFragment)
    }

    override fun uploadNewPage() {
        viewModel.loadNextPage()
    }


}