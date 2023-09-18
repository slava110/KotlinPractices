package com.slava_110.kotlinpractices.ui.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.slava_110.kotlinpractices.databinding.FragmentSimpleBinding
import com.slava_110.kotlinpractices.util.viewBinding

class SimpleFragment : Fragment() {
    val binding by viewBinding(FragmentSimpleBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}