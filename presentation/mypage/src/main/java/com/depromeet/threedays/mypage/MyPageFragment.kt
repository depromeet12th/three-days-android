package com.depromeet.threedays.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.domain.key.WEB_VIEW_URL
import com.depromeet.threedays.mypage.databinding.FragmentMyPageBinding
import com.depromeet.threedays.mypage.nickname.EditNicknameDialogFragment
import com.depromeet.threedays.navigator.ArchivedHabitNavigator
import com.depromeet.threedays.navigator.LicenseNavigator
import com.depromeet.threedays.navigator.PolicyNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel by viewModels<MyPageViewModel>()

    @Inject
    lateinit var archivedHabitNavigator: ArchivedHabitNavigator
    @Inject
    lateinit var policyNavigator: PolicyNavigator
    @Inject
    lateinit var licenseNavigator: LicenseNavigator

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
        val packageName = if (BuildConfig.DEBUG) {
            "com.depromeet.threedays.debug"
        } else {
            "com.depromeet.threedays"
        }
        val versionName = view.context.packageManager.getPackageInfo(packageName, 0,).versionName
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
        binding.tvPolicyService.setOnClickListener {
            onServicePolicyButtonClicked()
        }
        binding.tvPolicyPrivacy.setOnClickListener {
            onPrivacyPolicyButtonClicked()
        }
        binding.tvOpensourceLicense.setOnClickListener {
            onOpensourceLicenseButtonClicked()
        }
        binding.tvLogout.setOnClickListener {
            onLogoutButtonClicked()
        }
        binding.tvWithdraw.setOnClickListener {
            onWithdrawButtonClicked()
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

    /**
     * 마이페이지 > 이용약관
     */
    private fun onServicePolicyButtonClicked() {
        val intent = policyNavigator.intent(requireContext())
        // FIXME: debug, release 빌드 환경 따라 url 변경
        intent.putExtra(WEB_VIEW_URL, "https://zzaksim.notion.site/78e26f25307e4fcb9c6bc453e341e834")
        startActivity(intent)
    }

    /**
     * 마이페이지 > 개인정보처리방침
     */
    private fun onPrivacyPolicyButtonClicked() {
        val intent = policyNavigator.intent(requireContext())
        // FIXME: debug, release 빌드 환경 따라 url 변경
        intent.putExtra(WEB_VIEW_URL, "https://zzaksim.notion.site/a1e85e0b71ae42a4a970770f5b6cc885")
        startActivity(intent)
    }

    /**
     * 마이페이지 > 오픈소스 라이선스
     */
    private fun onOpensourceLicenseButtonClicked() {
        val intent = licenseNavigator.intent(requireContext())
        startActivity(intent)
    }

    /**
     * 마이페이지 > 로그아웃 버튼
     */
    private fun onLogoutButtonClicked() {
        viewModel.logout()
        // TODO: login 페이지로 이동
    }

    /**
     * 마이페이지 > 회원탈퇴 버튼
     */
    private fun onWithdrawButtonClicked() {
        viewModel.withdraw()
        // TODO: 로그인 페이지로 이동
    }
}
