package com.example.bnetapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bnetapp.R
import com.example.bnetapp.databinding.FragmentSingleCardBinding
import com.example.bnetapp.viewModel.MainViewModel
import kotlinx.coroutines.launch


class SingleCardFragment : Fragment() {

    private lateinit var binding: FragmentSingleCardBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSingleCardBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBarSingleCard.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_singleCardFragment_to_homeFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.singleDrugState.collect {
                    binding.singleCardTitle.text = it?.name
                    binding.singleCardSecondary.text = it?.description

                    Glide.with(requireContext()).load("http://shans.d2.i-partner.ru/${it?.image}")
                        .placeholder(R.drawable.placeholder)
                        .into(binding.singleCardImage)

                    Glide.with(requireContext()).load("http://shans.d2.i-partner.ru/${it?.categories?.icon}")
                        .placeholder(R.drawable.placeholder)
                        .into(binding.singleCardIcon)
                }
            }
        }


    }

}