package com.depromeet.threedays.mypage.nickname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.depromeet.threedays.mypage.R
import com.depromeet.threedays.mypage.databinding.FragmentEditNicknameDialogBinding
import com.depromeet.threedays.core_design_system.R as CoreDesignSystemResources

// FIXME: 다이얼로그 나오면 키보드 자동으로 띄우기
class EditNicknameDialogFragment(
    val nickname: String,
    val onSubmit: (String) -> Unit,
) : DialogFragment() {
    private var _binding: FragmentEditNicknameDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_nickname_dialog,
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
        initEvent()
    }

    private fun setLayout() {
        requireNotNull(dialog).apply {
            requireNotNull(window).apply {
                setLayout(
                    (resources.displayMetrics.widthPixels * 0.78).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawableResource(CoreDesignSystemResources.drawable.bg_rect_white_r18)
            }
        }
    }

    private fun initView() {
        binding.etNickname.apply {
            setText(nickname)
        }
//            requestFocus()
//            val inputMethodManager = getSystemService(context, InputMethodManager::class.java)
//            inputMethodManager?.showSoftInput(this, SHOW_IMPLICIT)
    }

    private fun initEvent() {
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
        binding.tvSubmit.setOnClickListener {
            val updatedNickname = binding.etNickname.text.toString()
            onSubmit(updatedNickname)
            dismiss()
        }

        binding.etNickname.apply {
            doAfterTextChanged {
                if (text.isBlank()) {
                    // disabled
                    binding.tvSubmit.setBackgroundResource(CoreDesignSystemResources.drawable.bg_rect_gray200_r10)
                    val gray450 = context.getColor(CoreDesignSystemResources.color.gray_450)
                    binding.tvSubmit.setTextColor(gray450)
                } else {
                    binding.tvSubmit.setBackgroundResource(CoreDesignSystemResources.drawable.bg_rect_main_r10)
                    val white = context.getColor(CoreDesignSystemResources.color.white)
                    binding.tvSubmit.setTextColor(white)
                }
                binding.tvCurrentLength.text = text.length.toString()
            }
        }
    }

    companion object {
        const val TAG = "EditNicknameDialogFragment"
    }
}
