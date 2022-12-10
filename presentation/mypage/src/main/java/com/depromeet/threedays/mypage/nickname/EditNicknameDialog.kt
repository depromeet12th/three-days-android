package com.depromeet.threedays.mypage.nickname

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.depromeet.threedays.mypage.databinding.DialogEditNicknameBinding

class EditNicknameDialog(
    context: Context,
    val nickname: String,
) : Dialog(context) {
    private lateinit var binding: DialogEditNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogEditNicknameBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
    }
}
