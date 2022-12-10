package com.depromeet.threedays.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.mypage.databinding.FragmentMyPageBinding
import com.depromeet.threedays.mypage.nickname.EditNicknameDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment: BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    private fun initEvent() {
        binding.ivEdit.setOnClickListener {
            onEditButtonClicked()
        }
    }

    private fun onEditButtonClicked() {
        val nickname = binding.tvNickname.text.toString()
        val dialog = EditNicknameDialog(requireContext(), nickname)
        dialog.show()
    }
}
