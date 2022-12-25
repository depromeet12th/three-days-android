package com.depromeet.threedays.home.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.home.R
import com.depromeet.threedays.core_design_system.R as core_design
import com.depromeet.threedays.home.databinding.BottomSheetNotiGuideBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotiGuideBottomSheet(
    private val moveToSettingForTurnOnPermission: () -> Unit,
) : BottomSheetDialogFragment()  {
    private var _binding: BottomSheetNotiGuideBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_noti_guide, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCancel.setOnSingleClickListener {
            dismiss()
        }
        binding.tvNotiOn.setOnSingleClickListener {
            dismiss()
            moveToSettingForTurnOnPermission()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "NotiGuideBottomSheet"
        fun newInstance(
            moveToSettingForTurnOnPermission: () -> Unit,
        ): NotiGuideBottomSheet {
            val modal = NotiGuideBottomSheet(moveToSettingForTurnOnPermission)
            modal.setStyle(DialogFragment.STYLE_NORMAL, core_design.style.RoundCornerBottomSheetDialogTheme)
            return modal
        }
    }
}
