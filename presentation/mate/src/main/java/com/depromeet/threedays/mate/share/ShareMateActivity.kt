package com.depromeet.threedays.mate.share

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.databinding.ActivityShareMateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

@AndroidEntryPoint
class ShareMateActivity : BaseActivity<ActivityShareMateBinding>(R.layout.activity_share_mate) {
    private val viewModel by viewModels<ShareMateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val habitId = intent.getLongExtra("habitId", -1)
        viewModel.fetchMate(habitId)
        initEvent()
        setObserve()
    }

    private fun initEvent() {
        binding.ivClose.setOnSingleClickListener {
            finish()
        }
        binding.tvImageSave.setOnSingleClickListener {
            saveScreenShot(binding.clScreenShotArea)
        }
    }

    private fun setObserve() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if(it.singleHabit != null) {
                        binding.singleHabit = it.singleHabit
                    }
                    setScreenShotBackgroundColor(it.backgroundResId)
                }
            }
        }
    }

    private fun setScreenShotBackgroundColor(resId: Int) {
        binding.clScreenShotArea.setBackgroundResource(resId)
    }

    // 특정 레이아웃 캡쳐해서 저장하기
    fun saveScreenShot(view: View?) {
        val title = Date() // 파일명 중복 방지를 위해 사용될 현재시간

        if (view == null) { // Null Point Exception ERROR 방지
            println("::::ERROR:::: view == NULL")
            return
        }

        /* 캡쳐 파일 저장 */
        view.buildDrawingCache() //캐시 비트 맵 만들기
        val bitmap = view.drawingCache

        var fos: OutputStream? = null

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver ->

                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, "$title.png")
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, "$title.png")
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            ThreeDaysToast().show(
                this,
                resources.getString(R.string.save_screen_shot_success)
            )
            finish()
        }
    }
}
