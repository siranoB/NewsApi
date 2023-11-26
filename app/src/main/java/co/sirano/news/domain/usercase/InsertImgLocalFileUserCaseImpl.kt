package co.sirano.news.domain.usercase

import co.sirano.news.data.local.entity.ImgLocalFileEntity
import co.sirano.news.domain.repository.Repository
import javax.inject.Inject

class InsertImgLocalFileUserCaseImpl @Inject constructor(private val repository: Repository) : InsertImgLocalFileUserCase {
    override suspend fun invoke(imgLocalFileEntity: ImgLocalFileEntity) {
        repository.insertNoLocalFileImgArticleEntity(imgLocalFileEntity)
    }
}
