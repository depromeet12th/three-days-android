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
    private lateinit var onPositiveAction: () -> Unit
    private var dialogMode: Int = NOT_INIT

    private var _binding: FragmentThreeDaysDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_three_days_dialog, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout(view)
        initEmoji()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLayout(view: View) {
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
        when (dialogMode) {
            DELETE_HABIT_NO_HISTORY, DELETE_HABIT_WITH_HISTORY, DELETE_HABIT_WITH_MATE -> {
                binding.tvEmoji.run {
                    text = TRASH_EMOJI.toEmoji
                    visible()
                }
            }
            CANCEL_HABIT_CREATE, CANCEL_HABIT_MODIFY -> {
                binding.tvEmoji.gone()
            }
        }
    }

    private fun initView() {
        binding.tvTitle.run {
            text = when (dialogMode) {
                DELETE_HABIT_NO_HISTORY -> getString(R.string.title_delete_habit)
                DELETE_HABIT_WITH_HISTORY -> getString(R.string.title_delete_habit)
                DELETE_HABIT_WITH_MATE -> getString(R.string.title_delete_habit_with_mate)
                CANCEL_HABIT_CREATE -> getString(R.string.title_cancel_habit_create)
                CANCEL_HABIT_MODIFY -> getString(R.string.title_cancel_habit_modify)
                else -> throw IllegalStateException()
            }
            margin(top = when(dialogMode) {
                DELETE_HABIT_NO_HISTORY, DELETE_HABIT_WITH_HISTORY, DELETE_HABIT_WITH_MATE -> 30F
                CANCEL_HABIT_CREATE, CANCEL_HABIT_MODIFY -> 28F
                else -> throw IllegalStateException()
            })
        }
        binding.tvDescription.run {
            text = when (dialogMode) {
                DELETE_HABIT_WITH_HISTORY -> getString(R.string.description_delete_habit_with_history)
                DELETE_HABIT_WITH_MATE -> getString(R.string.description_delete_habit_with_mate)
                CANCEL_HABIT_CREATE -> getString(R.string.description_cancel_habit_create)
                CANCEL_HABIT_MODIFY -> getString(R.string.description_cancel_habit_modify)
                else -> throw IllegalStateException()
            }
            visibleOrGone(dialogMode != DELETE_HABIT_NO_HISTORY)
        }

        binding.tvConfirm.run {
            text = when (dialogMode) {
                DELETE_HABIT_NO_HISTORY -> getString(R.string.reply_delete)
                DELETE_HABIT_WITH_HISTORY -> getString(R.string.reply_delete)
                DELETE_HABIT_WITH_MATE -> getString(R.string.reply_delete)
                CANCEL_HABIT_CREATE -> getString(R.string.reply_cancel)
                CANCEL_HABIT_MODIFY -> getString(R.string.reply_cancel)
                else -> throw IllegalStateException()
            }
            setOnSingleClickListener{
                onPositiveAction()
                dismiss()
            }
        }

        binding.tvCancel.run {
            margin(top = when(dialogMode) {
                DELETE_HABIT_NO_HISTORY, DELETE_HABIT_WITH_HISTORY, DELETE_HABIT_WITH_MATE -> 30F
                CANCEL_HABIT_CREATE, CANCEL_HABIT_MODIFY -> 20F
                else -> throw IllegalStateException()
            })
            setOnSingleClickListener {
                dismiss()
            }
        }
    }

    companion object {
        const val TAG = "ThreeDaysDialogFragment"

        const val NOT_INIT = -1
        const val DELETE_HABIT_NO_HISTORY = 0
        const val DELETE_HABIT_WITH_HISTORY = 1
        const val DELETE_HABIT_WITH_MATE = 2
        const val CANCEL_HABIT_CREATE = 3
        const val CANCEL_HABIT_MODIFY = 4

        const val TRASH_EMOJI = 0x1F5D1

        fun newInstance(dialogMode: Int, onPositiveAction: () -> Unit): ThreeDaysDialogFragment {
            val fragment = ThreeDaysDialogFragment()
            fragment.dialogMode = dialogMode
            fragment.onPositiveAction = onPositiveAction
            return fragment
        }
    }
}
