package co.sirano.news.di

import android.content.Context
import androidx.room.Room
import co.sirano.news.data.local.AppDatabase
import co.sirano.news.data.local.dao.ArticleDao
import co.sirano.news.data.local.dao.ImgLocalFileDao
import co.sirano.news.data.local.dao.RemoteKeysDao
import co.sirano.news.data.local.dao.ShowArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(
        appDatabase: AppDatabase
    ): ArticleDao {
        return appDatabase.articleDao()
    }

    @Singleton
    @Provides
    fun provideRemoteKeysDao(
        appDatabase: AppDatabase
    ): RemoteKeysDao {
        return appDatabase.remoteKeysDao()
    }

    @Singleton
    @Provides
    fun provideShowArticleDao(
        appDatabase: AppDatabase
    ): ShowArticleDao {
        return appDatabase.showArticleDao()
    }

    @Singleton
    @Provides
    fun provideImgLocalFileDao(
        appDatabase: AppDatabase
    ): ImgLocalFileDao {
        return appDatabase.imgLocalFileDao()
    }
}
