package com.slava_110.kotlinpractices.ui.webimage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.slava_110.kotlinpractices.databinding.FragmentWebImageBinding
import com.slava_110.kotlinpractices.util.viewBinding

class WebImageFragment : Fragment() {
    val viewBinding by viewBinding(FragmentWebImageBinding::bind)
    val viewModel by viewModels<WebImageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.imageBitmap.observe(viewLifecycleOwner) {
            viewBinding.imageView.setImageBitmap(viewModel.imageBitmap.value)
        }

        viewBinding.button.setOnClickListener {
            viewModel.loadImage(viewBinding.editTextText.text.toString())
        }
    }
}