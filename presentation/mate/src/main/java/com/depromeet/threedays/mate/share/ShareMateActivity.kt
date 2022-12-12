package com.depromeet.threedays.mate.share

import android.os.Bundle
import androidx.activity.viewModels
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.databinding.ActivityShareMateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareMateActivity : BaseActivity<ActivityShareMateBinding>(R.layout.activity_share_mate) {
    private val viewModel by viewModels<ShareMateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivClose.setOnSingleClickListener {
            finish()
        }
    }
}
