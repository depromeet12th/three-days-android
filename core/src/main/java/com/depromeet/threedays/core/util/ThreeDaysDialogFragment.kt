package com.depromeet.threedays.core.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.depromeet.threedays.core.R
import com.depromeet.threedays.core.databinding.FragmentThreeDaysDialogBinding
import com.depromeet.threedays.core.extensions.*
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.core_design_system.R.drawable.bg_rect_white_r18

class ThreeDaysDialogFragment : DialogFragment() {
    private var _binding: FragmentThreeDaysDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_three_days_dialog, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
        initEmoji()
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

    private fun initEmoji() {
        if (emoji != null) {
            binding.tvEmoji.text = emoji.toEmoji
            binding.tvEmoji.visible()
        }
    }

    private fun initView() {
        binding.tvTitle.run {
            text = title
            margin(
                top = if (titleTopMargin == 0f) {
                    if (emoji != null) TITLE_MARGIN_WITH_EMOJI
                    else TITLE_MARGIN_WITHOUT_EMOJI
                } else titleTopMargin
            )
        }
        binding.tvDescription.run {
            text = description
            visibleOrGone(description.isNotEmpty())
        }

        binding.tvConfirm.run {
            text = confirmText.ifEmpty { this.text }
            setOnSingleClickListener {
                onPositiveAction()
                dismiss()
            }
        }

        binding.tvCancel.run {
            text = cancelText.ifEmpty { this.text }
            margin(
                top = if (buttonTopMargin == 0f) {
                    if (emoji != null) BUTTON_MARGIN_WITH_EMOJI
                    else BUTTON_MARGIN_WITHOUT_EMOJI
                } else buttonTopMargin
            )
            setOnSingleClickListener {
                dismiss()
            }
        }
    }

    companion object {
        const val TAG = "ThreeDaysDialogFragment"

        const val TITLE_MARGIN_WITH_EMOJI = 30f
        const val TITLE_MARGIN_WITHOUT_EMOJI = 28f
        const val BUTTON_MARGIN_WITH_EMOJI = 30f
        const val BUTTON_MARGIN_WITHOUT_EMOJI = 20f

        private lateinit var onPositiveAction: () -> Unit
        private var emoji: Int? = null
        private var title: String = String.Empty
        private var description: String = String.Empty
        private var confirmText: String = String.Empty
        private var cancelText: String = String.Empty
        private var titleTopMargin: Float = 0F
        private var buttonTopMargin: Float = 0F

        fun newInstance(
            emoji: Int? = null,
            title: String,
            description: String = String.Empty,
            confirmText: String = String.Empty,
            cancelText: String = String.Empty,
            titleTopMargin: Float = 0F,
            buttonTopMargin: Float = 0F,
            onPositiveAction: () -> Unit
        ): ThreeDaysDialogFragment {
            this.emoji = emoji
            this.title = title
            this.description = description
            this.confirmText = confirmText
            this.cancelText = cancelText
            this.titleTopMargin = titleTopMargin
            this.buttonTopMargin = buttonTopMargin
            this.onPositiveAction = onPositiveAction
            return ThreeDaysDialogFragment()
        }
    }
}
