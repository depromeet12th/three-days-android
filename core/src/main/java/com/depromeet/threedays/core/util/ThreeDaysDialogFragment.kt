package com.depromeet.threedays.core.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.depromeet.threedays.core.R
import com.depromeet.threedays.core.databinding.FragmentThreeDaysDialogBinding
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.core.extensions.margin
import com.depromeet.threedays.core.extensions.visible
import com.depromeet.threedays.core.extensions.visibleOrGone
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.core_design_system.R.drawable.bg_rect_white_r18

class ThreeDaysDialogFragment : DialogFragment() {
    private var _binding: FragmentThreeDaysDialogBinding? = null
    private val binding get() = _binding!!
    private var data = DialogInfo.EMPTY

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
        initIconOrEmoji()
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

    private fun initIconOrEmoji() {
        if (data.emoji.isNotEmpty()) {
            binding.tvEmoji.text = data.emoji
            binding.tvEmoji.visible()
            binding.spaceEmojiArea.visible()
        } else {
            data.iconResId?.let {
                binding.ivIcon.apply {
                    setBackgroundResource(it)
                    visible()
                }
                binding.spaceEmojiArea.visible()
            }
        }
    }

    private fun initView() {
        binding.tvTitle.run {
            text = data.title
            margin(
                top = if (data.titleTopMargin == 0f) {
                    if (data.emoji.isNotEmpty()) TITLE_MARGIN_WITH_EMOJI
                    else TITLE_MARGIN_WITHOUT_EMOJI
                } else data.titleTopMargin
            )
        }
        binding.tvDescription.run {
            text = data.description
            visibleOrGone(data.description.isNotEmpty())
        }

        binding.tvConfirm.run {
            text = data.confirmText.ifEmpty { this.text }
            setOnSingleClickListener {
                data.onPositiveAction()
                dismiss()
            }
        }

        binding.tvCancel.run {
            text = data.cancelText.ifEmpty { this.text }
            margin(
                top = if (data.buttonTopMargin == 0f) {
                    if (data.emoji.isNotEmpty()) BUTTON_MARGIN_WITH_EMOJI
                    else BUTTON_MARGIN_WITHOUT_EMOJI
                } else data.buttonTopMargin
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

        fun newInstance(
            data: DialogInfo
        ): ThreeDaysDialogFragment {
            val fragment = ThreeDaysDialogFragment()
            fragment.data = data
            return fragment
        }
    }
}

data class DialogInfo (
    val onPositiveAction: () -> Unit,
    val iconResId: Int?,
    val emoji: String,
    val title: String,
    val description: String,
    val confirmText: String,
    val cancelText: String,
    val titleTopMargin: Float,
    val buttonTopMargin: Float,
) {
    companion object {
        val EMPTY = DialogInfo(
            onPositiveAction = { },
            iconResId = null,
            emoji = String.Empty,
            title = String.Empty,
            description = String.Empty,
            confirmText = String.Empty,
            cancelText = String.Empty,
            titleTopMargin = 0f,
            buttonTopMargin = 0f,
        )
    }
}
