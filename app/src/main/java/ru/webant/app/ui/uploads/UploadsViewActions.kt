package ru.webant.app.ui.uploads

import ru.webant.app.ui.base.BaseViewActions
import ru.webant.app.ui.uploads.recycler_view.UploadsModel
import java.io.File

sealed class UploadsViewActions : BaseViewActions {
    data class OnDeleteClicked(
        val item: UploadsModel
    ) : UploadsViewActions()

    data class OnImagesChosen(
        val file: File
    ) : UploadsViewActions()
}