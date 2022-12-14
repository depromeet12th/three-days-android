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

    // ?????? ???????????? ???????????? ????????????
    private fun saveScreenShot(view: View?) {
        val title = Date() // ????????? ?????? ????????? ?????? ????????? ????????????

        if (view == null) { // Null Point Exception ERROR ??????
            println("::::ERROR:::: view == NULL")
            return
        }

        /* ?????? ?????? ?????? */
        view.buildDrawingCache() //?????? ?????? ??? ?????????
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

    // ??? ???????????? ??????????????? ????????? ?????? ?????? ??????
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
            Toast.makeText(this, "???????????? ???????????????.", Toast.LENGTH_SHORT).show()
        }
    }

    // ????????? ????????? View??? Bitmap??? ?????? ??????.
    private fun drawBackgroundBitmap(): Bitmap {
        //?????? ???????????? ?????????.
        val backgroundWidth = resources.displayMetrics.widthPixels
        val backgroundHeight = resources.displayMetrics.heightPixels

        val backgroundBitmap = Bitmap.createBitmap(backgroundWidth, backgroundHeight, Bitmap.Config.ARGB_8888) // ????????? ??????
        val canvas = Canvas(backgroundBitmap) // ???????????? ???????????? Mapping.
        canvas.drawColor(ContextCompat.getColor(this, com.depromeet.threedays.core_design_system.R.color.black)) // ???????????? ?????? ????????? ????????????????????? ?????????.

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

    //Android Q (Android 10, API 29 ??????????????? ??? ???????????? ????????? ???????????? ????????????.)
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageOnAboveAndroidQ(bitmap: Bitmap): Uri? {
        val fileName = System.currentTimeMillis().toString() + ".png" // ???????????? ????????????.png

        val contentValues = ContentValues()
        contentValues.apply {
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/ImageSave") // ?????? ??????
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName) // ??????????????? put?????????.
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.IS_PENDING, 1) // ?????? is_pending ???????????? ???????????????.
            // ?????? ????????? ??? ???????????? ???????????? ??????????????? ?????????, ?????? ???????????? ????????? ??? ??????.
        }

        // ???????????? ????????? uri??? ?????? ??????????????????.
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            if(uri != null) {
                val image = contentResolver.openFileDescriptor(uri, "w", null)
                // write ????????? file??? open??????.

                if(image != null) {
                    val fos = FileOutputStream(image.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    //???????????? FileOutputStream??? ?????? compress??????.
                    fos.close()

                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0) // ????????? ????????? ????????????.
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

    // TODO: ??????????????? Q ?????? ?????? ??????????????? ???????????? ??????
    /* 
    private fun saveImageOnUnderAndroidQ(bitmap: Bitmap): Uri? {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val externalStorage = Environment.getExternalStorageDirectory().absolutePath
        val path = "$externalStorage/DCIM/imageSave"
        val dir = File(path)

        if(dir.exists().not()) {
            dir.mkdirs() // ?????? ???????????? ?????? ??????
        }

        val fileItem = File("$dir/$fileName")
        try {
            fileItem.createNewFile()
            //0KB ?????? ??????.

            val fos = FileOutputStream(fileItem) // ?????? ????????? ?????????

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            //?????? ????????? ????????? ????????? ????????? Bitmap ??????.

            fos.close() // ?????? ????????? ????????? ?????? close

            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
            // ?????????????????? ??????????????? ?????? ????????? ?????? ?????? ??????. ????????? ???????????? ????????? ????????? Uri??? ????????????.
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
