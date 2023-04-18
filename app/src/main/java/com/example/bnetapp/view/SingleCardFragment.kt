package com.example.bnetapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bnetapp.R
import com.example.bnetapp.databinding.FragmentSingleCardBinding


class SingleCardFragment : Fragment() {

    private lateinit var binding: FragmentSingleCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSingleCardBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

}