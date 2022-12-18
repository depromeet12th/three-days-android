package com.depromeet.threedays.create.emoji

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.threedays.create.R
import com.depromeet.threedays.create.databinding.FragmentEmojiBottomSheetDialogBinding
import com.depromeet.threedays.domain.entity.emoji.Emoji
import com.depromeet.threedays.domain.entity.emoji.EmojiUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EmojiBottomSheetDialogFragment: BottomSheetDialogFragment() {
    private var _binding: FragmentEmojiBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        EmojiListAdapter(
            onEmojiClick = { emoji ->
                onEmojiClick(emoji)

                this.dismiss()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentEmojiBottomSheetDialogBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun getTheme(): Int {
        return com.depromeet.threedays.core_design_system.R.style.RoundCornerBottomSheetDialogTheme
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.rvEmojiList.adapter = adapter
        adapter.submitList(EmojiUtil.getEmojiList(EmojiUtil.Category.FACE).toEmojiItemList())
        binding.rvEmojiList.layoutManager = GridLayoutManager(requireContext(), 6)
        
        binding.groupEmoji.setOnCheckedChangeListener{_, index ->
            when(index) {
                R.id.rb_face -> adapter.submitList(EmojiUtil.getEmojiList(EmojiUtil.Category.FACE).toEmojiItemList())
                R.id.rb_food -> adapter.submitList(EmojiUtil.getEmojiList(EmojiUtil.Category.FOOD).toEmojiItemList())
                R.id.rb_animal -> adapter.submitList(EmojiUtil.getEmojiList(EmojiUtil.Category.ANIMAL).toEmojiItemList())
                R.id.rb_activity -> adapter.submitList(EmojiUtil.getEmojiList(EmojiUtil.Category.ACTIVITY).toEmojiItemList())
                R.id.rb_object -> adapter.submitList(EmojiUtil.getEmojiList(EmojiUtil.Category.OBJECT).toEmojiItemList())
            }
        }
    }

    companion object {
        const val TAG = "EmojiBottomSheetDialogFragment"

        lateinit var onEmojiClick: (emoji: Emoji) -> Unit

        fun newInstance(
            onEmojiClick: (emoji: Emoji) -> Unit
        ): EmojiBottomSheetDialogFragment {
            this.onEmojiClick = onEmojiClick
            return EmojiBottomSheetDialogFragment()
        }
    }
}
