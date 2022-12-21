package com.depromeet.threedays.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.mypage.databinding.FragmentMyPageBinding
import com.depromeet.threedays.mypage.nickname.EditNicknameDialogFragment
import com.depromeet.threedays.navigator.ArchivedHabitNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel by viewModels<MyPageViewModel>()

    @Inject
    lateinit var archivedHabitNavigator: ArchivedHabitNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchMyInfo()

        initEvent()
        initView(view)
        setObserve()
    }

    /**
     * 화면 초기화
     */
    private fun initView(view: View) {
        // FIXME: getPackageInfo(String, PackageInfoFlags) 써보려했으나 에러나서 일단 동작하는 코드 사용함
        val versionName = view.context.packageManager.getPackageInfo("com.depromeet.threedays", 0,).versionName
        binding.tvAppVersionName.text = versionName
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

    /**
     * observer 초기화
     */
    private fun setObserve() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.nickname.collect { nickname ->
                    binding.tvNickname.text = nickname
                }
            }
        }
    }

    /**
     * 마이페이지 > 습관 보관함 버튼
     */
    private fun onHabitArchivedButtonClicked() {
        val intent = archivedHabitNavigator.intent(requireContext())
        startActivity(intent)
    }

    /**
     * 마이페이지 > 닉네임 수정 버튼
     */
    private fun onEditButtonClicked() {
        val nickname = binding.tvNickname.text.toString()
        EditNicknameDialogFragment(
            nickname = nickname,
            onSubmit = {
                viewModel.updateNickname(nickname = it)
                ThreeDaysToast().show(requireContext(), "닉네임이 변경됐어요.")
            },
        ).show(
            requireActivity().supportFragmentManager,
            EditNicknameDialogFragment.TAG,
        )
    }

    private fun onServicePolicyButtonClicked() {
        TODO("이용 약관")
    }

    private fun onPrivacyPolicyButtonClicked() {
        TODO("개인정보처리방침")
    }

}
