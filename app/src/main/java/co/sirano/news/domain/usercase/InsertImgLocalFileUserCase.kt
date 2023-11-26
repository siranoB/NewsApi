package co.sirano.news.domain.usercase

import co.sirano.news.data.local.entity.ImgLocalFileEntity

interface InsertImgLocalFileUserCase {
    suspend fun invoke(imgLocalFileEntity: ImgLocalFileEntity)
}
