package com.depromeet.threedays.home.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.depromeet.threedays.core.analytics.*
import com.depromeet.threedays.core.util.setOnSingleClickListener
import com.depromeet.threedays.home.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotiRecommendBottomSheet(
    val onConfirmClick: () -> Unit
) : BottomSheetDialogFragment()  {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AnalyticsUtil.event(
            name = ThreeDaysEvent.PushViewed.toString(),
            properties = mapOf(
                MixPanelEvent.ScreenName to Screen.Push.toString()
            )
        )

        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottom_sheet_noti_recommend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnConfirm = view.findViewById<Button>(R.id.btn_confirm)
        btnConfirm.setOnSingleClickListener {
            AnalyticsUtil.event(
                name = ThreeDaysEvent.ButtonClicked.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to Screen.Push.toString(),
                    MixPanelEvent.ButtonType to ButtonType.Next.toString()
                )
            )
            dismiss()
            onConfirmClick()
        }
    }

    companion object {
        const val TAG = "NotiRecommendBottomSheet"
    }
}
