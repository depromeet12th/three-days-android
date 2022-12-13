package com.depromeet.threedays.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.mypage.databinding.FragmentMyPageBinding
import com.depromeet.threedays.mypage.nickname.EditNicknameDialogFragment
import com.depromeet.threedays.navigator.ArchivedHabitNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPageFragment: BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel by viewModels<MyPageViewModel>()

    @Inject
    lateinit var archivedHabitNavigator: ArchivedHabitNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    /**
     * 이벤트 관련 작업 초기화
     */
    private fun initEvent() {
        binding.ivHabitArchived.setOnClickListener {
            onHabitArchivedButtonClicked()
        }
        binding.ivEdit.setOnClickListener {
            onEditButtonClicked()
        }
    }

    private fun onHabitArchivedButtonClicked() {
        val intent = archivedHabitNavigator.intent(requireContext())
        startActivity(intent)
    }

    private fun onEditButtonClicked() {
        val nickname = binding.tvNickname.text.toString()
        EditNicknameDialogFragment(
            nickname = nickname,
            onComplete = { ThreeDaysToast().show(requireContext(), "닉네임이 변경됐어요.") } ,
        ).show(
            requireActivity().supportFragmentManager,
            EditNicknameDialogFragment.TAG,
        )
    }

    private fun onVersionButtonClicked() {
        TODO("버전 정보")
    }

    private fun onServicePolicyButtonClicked() {
        TODO("이용 약관")
    }

    private fun onPrivacyPolicyButtonClicked() {
        TODO("개인정보처리방침")
    }

}
