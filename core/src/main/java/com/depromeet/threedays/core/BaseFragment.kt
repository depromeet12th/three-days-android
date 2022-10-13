package com.depromeet.threedays.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import java.lang.IllegalStateException

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes layout: Int) : Fragment(layout) {

    protected var _binding: VB? = null
    val binding: VB
        get() = _binding ?: throw IllegalStateException("_binding is not initialized !")

    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    protected fun onBind(body: VB.() -> Unit) {
        binding.run(body)
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}
