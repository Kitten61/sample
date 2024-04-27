package ru.webant.app.ui.uploads

import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.webant.app.di.viewmodel.AssistedViewModelFactory
import ru.webant.app.di.viewmodel.hiltMavericksViewModelFactory
import ru.webant.app.ui.base.pagination.BasePaginationViewModel
import ru.webant.app.ui.uploads.recycler_view.UploadsModel
import ru.webant.domain.entities.ImageEntity
import ru.webant.domain.error.ErrorHandler
import ru.webant.domain.gateway.ImagesGateway
import ru.webant.gateway.utils.toResult
import ru.webant.domain.result.Result
import kotlin.reflect.KClass

class UploadsViewModel @AssistedInject constructor(
    @Assisted state: UploadsViewState,
    override val errorHandler: ErrorHandler,
    private val imagesGateway: ImagesGateway
) : BasePaginationViewModel<
        UploadsModel,
        UploadsViewState,
        UploadsViewEvents,
        ImageEntity,
        UploadsViewActions
>(state) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<UploadsViewModel, UploadsViewState>

    init {
        loadFirstPage(isNeedToShowSwipeRefresh = true)
    }

    override fun getItems(page: Int): Single<List<ImageEntity>> {
        return imagesGateway.getUserImages(page)
    }

    override fun getStateKClass(): KClass<UploadsViewState> {
        return UploadsViewState::class
    }

    override fun handle(action: UploadsViewActions) {
        when (action) {
            is UploadsViewActions.OnDeleteClicked -> handleOnDeleteClicked(action)
            is UploadsViewActions.OnImagesChosen -> handleFileUpload(action)
        }
    }

    override fun screenModelMapper(baseEntities: List<ImageEntity>): List<UploadsModel> {
        return baseEntities.map {
            UploadsModel(
                id = it.id,
                url = it.url
            )
        }
    }

    private fun handleFileUpload(action: UploadsViewActions.OnImagesChosen) {
        val filePart = action.file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData(
            "file",
            action.file.name,
            filePart
        )

        imagesGateway.uploadFile(part)
            .subscribeOn(Schedulers.io())
            .toResult(errorHandler)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                it.printStackTrace()
            })
            .disposeOnCleared()
    }

    private fun handleOnDeleteClicked(action: UploadsViewActions.OnDeleteClicked) {
        imagesGateway.deleteFile(action.item.id)
            .subscribeOn(Schedulers.io())
            .toResult(errorHandler)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                when (result) {
                    is Result.Success -> {
                        setState {
                            copy(data = data.filter {
                                it.id != action.item.id
                            })
                        }
                    }
                    is Result.Error -> {}
                }
            }, {
                it.printStackTrace()
            })
            .disposeOnCleared()
    }

    companion object :
        MavericksViewModelFactory<UploadsViewModel, UploadsViewState> by hiltMavericksViewModelFactory()

}