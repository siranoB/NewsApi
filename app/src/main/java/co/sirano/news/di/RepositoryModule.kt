package co.sirano.news.di

import co.sirano.news.data.repositoryImpl.RepositoryImpl
import co.sirano.news.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindsRepository(repository: RepositoryImpl): Repository
}
