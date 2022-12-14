package com.depromeet.threedays.core.util

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.depromeet.threedays.core.R
import com.depromeet.threedays.core.databinding.FragmentRangeTimePickerDialogBinding
import com.depromeet.threedays.core.setOnSingleClickListener
import java.time.LocalTime

class RangeTimePickerDialogFragment : DialogFragment() {
    private var _binding: FragmentRangeTimePickerDialogBinding? = null
    private val binding get() = _binding!!

    private var time = LocalTime.now()
    private lateinit var onConfirmClickListener: (time: LocalTime) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_range_time_picker_dialog,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
        initView()
        setTimePicker()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLayout() {
        requireNotNull(dialog).apply {
            requireNotNull(window).apply {
                setBackgroundDrawableResource(com.depromeet.threedays.core_design_system.R.drawable.bg_rect_white_r18)
            }
        }
    }

    private fun initView() {
        binding.timePicker.hour = time.hour
        binding.timePicker.minute = time.minute / TIME_PICKER_INTERVAL_MINUTE

        binding.tvCancel.setOnSingleClickListener { dismiss() }

        binding.tvConfirm.setOnSingleClickListener {
            onConfirmClickListener.invoke(
                LocalTime.of(binding.timePicker.hour, binding.timePicker.minute * TIME_PICKER_INTERVAL_MINUTE)
            )
            dismiss()
        }
    }

    private fun setTimePicker() {
        (binding.timePicker.findViewById(
            Resources.getSystem().getIdentifier("minute", "id", "android")
        ) as NumberPicker).apply {
            minValue = 0
            maxValue = 60 / TIME_PICKER_INTERVAL_MINUTE - 1
            displayedValues = getDisplayedValue().toTypedArray()
            setOnValueChangedListener(null)
        }
    }

    private fun getDisplayedValue(
    ): MutableList<String> {
        val displayedValues: MutableList<String> = java.util.ArrayList()
        for (i in 0..59 step TIME_PICKER_INTERVAL_MINUTE)
            displayedValues.add(String.format("%02d", i))
        return displayedValues
    }

    companion object {
        const val TAG = "RangeTimePickerDialogFragment"

        const val TIME_PICKER_INTERVAL_MINUTE = 30

        fun newInstance(
            time: LocalTime, onConfirmClickListener: (time: LocalTime) -> Unit
        ): RangeTimePickerDialogFragment {
            val fragment = RangeTimePickerDialogFragment()
            fragment.time = time
            fragment.onConfirmClickListener = onConfirmClickListener
            return fragment
        }
    }
}
