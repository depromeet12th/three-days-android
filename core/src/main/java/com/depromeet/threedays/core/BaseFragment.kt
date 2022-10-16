package com.depromeet.threedays.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import java.lang.IllegalStateException

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes private val layout: Int) : Fragment(layout) {

    protected var _binding: VB? = null
    val binding: VB
        get() = _binding ?: throw IllegalStateException("_binding is not initialized !")

    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.lifecycleOwner = viewLifecycleOwner
    }

    protected fun onBind(body: VB.() -> Unit) {
        binding.run(body)
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}
