package com.depromeet.threedays.mate.share

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.analytics.*
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.core.util.setOnSingleClickListener
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.create.step1.model.toMateUI
import com.depromeet.threedays.mate.databinding.ActivityShareMateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.*
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
            AnalyticsUtil.event(
                name = ThreeDaysEvent.ButtonClicked.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to Screen.MateShare.toString(),
                    MixPanelEvent.ButtonType to ButtonType.Close.toString(),
                )
            )

            finish()
        }
        binding.tvImageSave.setOnSingleClickListener {
            AnalyticsUtil.event(
                name = ThreeDaysEvent.SharedPathClicked.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to Screen.MateShare.toString(),
                    MixPanelEvent.ButtonType to ButtonType.SaveImg.toString(),
                )
            )

            saveScreenShot(binding.clScreenShotArea)
        }
        binding.tvInstagramShare.setOnSingleClickListener {
            AnalyticsUtil.event(
                name = ThreeDaysEvent.SharedPathClicked.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to Screen.MateShare.toString(),
                    MixPanelEvent.ButtonType to ButtonType.Insta.toString(),
                )
            )

            startInstagramIntent()
        }
    }

    private fun setObserve() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.singleHabit != null) {
                        binding.singleHabit = it.singleHabit
                        it.singleHabit.mate?.run {
                            val mageImageResourceId = this.toMateUI().resolveMateImageResource()
                            binding.ivMate.setImageResource(mageImageResourceId)
                        }
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
    private fun saveScreenShot(view: View?) {
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

    // 이 아래로는 인스타그램 스토리 공유 관련 코드
    private fun startInstagramIntent() {
        val bgBitmap = drawBackgroundBitmap()
        val viewBitmap = drawViewBitmap()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val backgroundAssetUri = saveImageOnAboveAndroidQ(bgBitmap)
            val stickerAssetUri = saveImageOnAboveAndroidQ(viewBitmap)

            val intent = Intent("com.instagram.share.ADD_TO_STORY").apply {
                setDataAndType(backgroundAssetUri, "image/*")
                putExtra("interactive_asset_uri", stickerAssetUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setPackage("com.instagram.android")
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "준비중인 기능입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 화면에 나타난 View를 Bitmap에 그릴 용도.
    private fun drawBackgroundBitmap(): Bitmap {
        //기기 해상도를 가져옴.
        val backgroundWidth = resources.displayMetrics.widthPixels
        val backgroundHeight = resources.displayMetrics.heightPixels

        val backgroundBitmap = Bitmap.createBitmap(backgroundWidth, backgroundHeight, Bitmap.Config.ARGB_8888) // 비트맵 생성
        val canvas = Canvas(backgroundBitmap) // 캔버스에 비트맵을 Mapping.
        canvas.drawColor(ContextCompat.getColor(this, com.depromeet.threedays.core_design_system.R.color.black)) // 캔버스에 현재 설정된 배경화면색으로 칠한다.

        return backgroundBitmap
    }

    // references: https://kimyunseok.tistory.com/139
    private fun drawViewBitmap(): Bitmap {
        val imageView = binding.clScreenShotArea

        val margin = resources.displayMetrics.density * 20
        val width = imageView.width
        val height = (imageView.height + margin).toInt()

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val imageViewBitmap = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
        val imageViewCanvas = Canvas(imageViewBitmap)
        imageView.draw(imageViewCanvas)

        val imageViewLeft = ((width - imageView.width) / 2).toFloat()
        canvas.drawBitmap(imageViewBitmap, imageViewLeft, (0).toFloat(), null)

        return bitmap
    }

    //Android Q (Android 10, API 29 이상에서는 이 메서드를 통해서 이미지를 저장한다.)
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageOnAboveAndroidQ(bitmap: Bitmap): Uri? {
        val fileName = System.currentTimeMillis().toString() + ".png" // 파일이름 현재시간.png

        val contentValues = ContentValues()
        contentValues.apply {
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/ImageSave") // 경로 설정
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName) // 파일이름을 put해준다.
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.IS_PENDING, 1) // 현재 is_pending 상태임을 만들어준다.
            // 다른 곳에서 이 데이터를 요구하면 무시하라는 의미로, 해당 저장소를 독점할 수 있다.
        }

        // 이미지를 저장할 uri를 미리 설정해놓는다.
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            if(uri != null) {
                val image = contentResolver.openFileDescriptor(uri, "w", null)
                // write 모드로 file을 open한다.

                if(image != null) {
                    val fos = FileOutputStream(image.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    //비트맵을 FileOutputStream를 통해 compress한다.
                    fos.close()

                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0) // 저장소 독점을 해제한다.
                    contentResolver.update(uri, contentValues, null, null)
                }
            }
        } catch(e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return uri
    }

    // TODO: 안드로이드 Q 버전 이하 가능하도록 업데이트 필요
    /* 
    private fun saveImageOnUnderAndroidQ(bitmap: Bitmap): Uri? {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val externalStorage = Environment.getExternalStorageDirectory().absolutePath
        val path = "$externalStorage/DCIM/imageSave"
        val dir = File(path)

        if(dir.exists().not()) {
            dir.mkdirs() // 폴더 없을경우 폴더 생성
        }

        val fileItem = File("$dir/$fileName")
        try {
            fileItem.createNewFile()
            //0KB 파일 생성.

            val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            //파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

            fos.close() // 파일 아웃풋 스트림 객체 close

            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
            // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다.
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return FileProvider.getUriForFile(this, "$packageName.fileprovider", fileItem)
    }
     */
}
