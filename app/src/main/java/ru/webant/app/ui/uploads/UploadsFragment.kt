package ru.webant.app.ui.uploads

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.mvrx.fragmentViewModel
import org.apache.commons.io.IOUtils
import ru.webant.app.databinding.FragmentCatsBinding
import ru.webant.app.ui.base.pagination.BasePaginationFragment
import ru.webant.app.ui.uploads.recycler_view.UploadsAdapter
import ru.webant.app.ui.uploads.recycler_view.UploadsModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class UploadsFragment:  BasePaginationFragment<UploadsModel, FragmentCatsBinding, UploadsViewEvents>() {

    override val viewModel: UploadsViewModel by fragmentViewModel()


    override val recyclerView: RecyclerView by lazy {
        views.rvItems
    }
    override val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        views.swipeRefreshLayout
    }

    override val adapter: UploadsAdapter = UploadsAdapter(
        object: UploadsAdapter.Callback {
            override fun onItemLongClickListener(item: UploadsModel) {
                viewModel.handle(UploadsViewActions.OnDeleteClicked(item))
            }

            override fun onItemClicked(item: UploadsModel) {
                //TODO("Not yet implemented")
            }

        }
    )

    override fun setupAdapters() {
        views.rvItems.adapter = adapter
        views.rvItems.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
    }

    override fun setupUi() {
        views.fabUpload.isVisible = true
    }

    override fun setupListeners() {
        super.setupListeners()
        views.fabUpload.setOnClickListener {
            choosePhotoFromGalleryResultLauncher.launch("image/*")
        }
    }

    override fun getBinding(): FragmentCatsBinding = FragmentCatsBinding.inflate(layoutInflater)

    private val choosePhotoFromGalleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val parcelFileDescriptor = requireContext()
                    .contentResolver
                    .openFileDescriptor(uri, "r", null)

                parcelFileDescriptor?.let {
                    val inputStream = FileInputStream(it.fileDescriptor)
                    val file = File(
                        requireContext().cacheDir,
                        requireContext().contentResolver.getFileName(uri)
                    )
                    val outputStream = FileOutputStream(file)
                    IOUtils.copy(inputStream, outputStream)
                    viewModel.handle(UploadsViewActions.OnImagesChosen(file))
                }

            }
        }

    fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }

        return name
    }
}