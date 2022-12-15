package com.depromeet.threedays.core.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.depromeet.threedays.core.R
import com.depromeet.threedays.core.databinding.FragmentThreeDaysOneButtonDialogBinding
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.core.extensions.visible
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.core_design_system.R.drawable.bg_rect_white_r18

class ThreeDaysOneButtonDialogFragment : DialogFragment() {
    private var _binding: FragmentThreeDaysOneButtonDialogBinding? = null
    private val binding get() = _binding!!
    private var data = OneButtonDialogInfo.EMPTY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_three_days_one_button_dialog, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLayout() {
        requireNotNull(dialog).apply {
            requireNotNull(window).apply {
                setLayout(
                    (resources.displayMetrics.widthPixels * 0.78).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawableResource(bg_rect_white_r18)
            }
        }
    }

    private fun initView() {
        if(data.level != null) {
            binding.tvLevel.run {
                visible()
                text = String.format(getString(R.string.mate_level), data.level)
            }
        }
        binding.tvTitle.text = data.title
        binding.tvDescription.text = data.description
        binding.tvCancel.setOnSingleClickListener {
            dismiss()
        }
        context?.let {
            Glide.with(it)
                .load(data.imageUrl)
                .placeholder(com.depromeet.threedays.core_design_system.R.drawable.bg_rect_gray200_r10)
                .into(binding.ivIllustration)
        }
    }

    companion object {
        const val TAG = "ThreeDaysDialogOneButtonFragment"

        fun newInstance(
            data: OneButtonDialogInfo
        ): ThreeDaysOneButtonDialogFragment {
            val fragment = ThreeDaysOneButtonDialogFragment()
            fragment.data = data
            return fragment
        }
    }
}

data class OneButtonDialogInfo (
    val imageUrl: String,
    val level: Int?,
    val title: String,
    val description: String
) {
    companion object {
        val EMPTY = OneButtonDialogInfo(
            imageUrl = String.Empty,
            title = String.Empty,
            level = null,
            description = String.Empty
        )
    }
}
