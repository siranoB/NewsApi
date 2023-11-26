package co.sirano.news.di

import co.sirano.news.domain.usercase.GetNoLocalFileImgArticleEntityListUserCase
import co.sirano.news.domain.usercase.GetNoLocalFileImgArticleEntityListUserCaseImpl
import co.sirano.news.domain.usercase.GetTopHeadLinesUseCase
import co.sirano.news.domain.usercase.GetTopHeadLinesUseCaseImpl
import co.sirano.news.domain.usercase.InsertImgLocalFileUserCase
import co.sirano.news.domain.usercase.InsertImgLocalFileUserCaseImpl
import co.sirano.news.domain.usercase.UpdateArticleEntityUserCase
import co.sirano.news.domain.usercase.UpdateArticleEntityUserCaseImpl
import co.sirano.news.domain.usercase.UpdateShowArticleEntityUserCase
import co.sirano.news.domain.usercase.UpdateShowArticleEntityUserCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Singleton
    @Binds
    abstract fun bindsGetTopHeadLinesUseCase(useCase: GetTopHeadLinesUseCaseImpl): GetTopHeadLinesUseCase

    @Singleton
    @Binds
    abstract fun bindsUpdateArticleEntityUserCase(useCase: UpdateArticleEntityUserCaseImpl): UpdateArticleEntityUserCase

    @Singleton
    @Binds
    abstract fun bindsUpdateShowArticleEntityUserCase(useCase: UpdateShowArticleEntityUserCaseImpl): UpdateShowArticleEntityUserCase

    @Singleton
    @Binds
    abstract fun bindsGetNoLocalFileImgArticleEntityListUserCase(useCase: GetNoLocalFileImgArticleEntityListUserCaseImpl): GetNoLocalFileImgArticleEntityListUserCase

    @Singleton
    @Binds
    abstract fun bindsInsertImgLocalFileUserCase(useCase: InsertImgLocalFileUserCaseImpl): InsertImgLocalFileUserCase
}
