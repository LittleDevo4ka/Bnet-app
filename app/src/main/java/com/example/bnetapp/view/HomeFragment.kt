package com.example.bnetapp.view

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.bnetapp.R
import com.example.bnetapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchEditText.visibility = View.GONE
        binding.topAppBarHome.navigationIcon = null

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




    }


}