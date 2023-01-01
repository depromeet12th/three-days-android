package com.depromeet.threedays.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.depromeet.threedays.onboarding.databinding.FragmentOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    private var title: String? = null
    private var content: String? = null
    private var onboardingImageResId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_PARAM1)
            content = it.getString(ARG_PARAM2)
            onboardingImageResId = it.getInt(ARG_PARAM3)
        }

        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = title
        binding.tvContent.text = content
        onboardingImageResId?.let {
            binding.ivOnboarding.setBackgroundResource(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, content: String, onboardingImageResId: Int) =
            OnboardingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, title)
                    putString(ARG_PARAM2, content)
                    putInt(ARG_PARAM3, onboardingImageResId)
                }
            }
    }
}
