package com.slava_110.kotlinpractices.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.slava_110.kotlinpractices.R
import com.slava_110.kotlinpractices.databinding.FragmentMainBinding
import com.slava_110.kotlinpractices.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    val viewModel by viewModels<MainViewModel>()
    val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}