package com.depromeet.threedays.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.depromeet.threedays.home.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotiGuideBottomSheet : BottomSheetDialogFragment()  {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottom_sheet_noti_guide, container, false)
    }

    companion object {
        const val TAG = "NotiGuideBottomSheet"
    }
}
