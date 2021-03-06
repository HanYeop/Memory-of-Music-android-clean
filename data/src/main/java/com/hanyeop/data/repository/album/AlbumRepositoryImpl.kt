package com.hanyeop.data.repository.album

import com.hanyeop.data.mapper.mapperToAlbum
import com.hanyeop.data.mapper.mapperToAlbumEntity
import com.hanyeop.data.mapper.mapperToAlbumResponse
import com.hanyeop.data.repository.album.local.AlbumLocalDataSource
import com.hanyeop.data.repository.album.remote.AlbumRemoteDataSource
import com.hanyeop.domain.model.album.Album
import com.hanyeop.domain.model.album.DomainAlbumResponse
import com.hanyeop.domain.repository.AlbumRepository
import com.hanyeop.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val albumLocalDataSource: AlbumLocalDataSource,
    private val albumRemoteDataSource: AlbumRemoteDataSource
) : AlbumRepository {
    override fun insertAlbum(album: Album) = albumLocalDataSource.insertAlbum(mapperToAlbumEntity(album))

    override fun getAllAlbum(): Flow<Result<List<Album>>> = flow {
        emit(Result.Loading)
        albumLocalDataSource.getAllAlbum().collect {
            if(it.isEmpty()){
                emit(Result.Empty)
            }else{
                emit(Result.Success(mapperToAlbum(it)))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    override fun getRemoteAlbums(keyword: String): Flow<Result<List<DomainAlbumResponse>>>  = flow {
        emit(Result.Loading)
        albumRemoteDataSource.getRemoteAlbums(keyword).collect {
            emit(Result.Success(mapperToAlbumResponse(it)))
        }
    }.catch { e
        -> emit(Result.Error(e))
    }

    override fun deleteAlbum(id: Int) = albumLocalDataSource.deleteAlbum(id)

    override fun updateAlbum(id: Int, title: String, artist: String, genre: String, rating: Float, summary: String, content: String)
        = albumLocalDataSource.updateAlbum(id, title, artist, genre, rating, summary, content)

    override fun getAllAlbumByRating(start: Float, end: Float): Flow<Result<List<Album>>> = flow {
        emit(Result.Loading)
        albumLocalDataSource.getAllAlbumByRating(start, end).collect {
            if(it.isEmpty()){
                emit(Result.Empty)
            }else{
                emit(Result.Success(mapperToAlbum(it)))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    override fun getAllAlbumByCategory(start: Float, end: Float, genre: String): Flow<Result<List<Album>>> = flow {
        emit(Result.Loading)
        albumLocalDataSource.getAllAlbumByCategory(start, end, genre).collect {
            if(it.isEmpty()){
                emit(Result.Empty)
            }else{
                emit(Result.Success(mapperToAlbum(it)))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    override fun getAllAlbumCount(): Flow<Result<Int>> = flow {
        emit(Result.Loading)
        albumLocalDataSource.getAllAlbumCount().collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    override fun deleteAllAlbum() = albumLocalDataSource.deleteAllAlbum()
}