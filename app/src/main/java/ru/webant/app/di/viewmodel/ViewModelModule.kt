package ru.webant.app.di.viewmodel

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap
import ru.webant.app.ui.cats.CatsViewModel
import ru.webant.app.ui.favorites.FavoritesViewModel
import ru.webant.app.ui.main.MainViewModel
import ru.webant.app.ui.uploads.UploadsViewModel

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModelFactory(factory: MainViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(CatsViewModel::class)
    fun bindCatsViewModelFactory(factory: CatsViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    fun bindFavoritesViewModelFactory(factory: FavoritesViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(UploadsViewModel::class)
    fun bindUploadsViewModelFactory(factory: UploadsViewModel.Factory): AssistedViewModelFactory<*, *>
}