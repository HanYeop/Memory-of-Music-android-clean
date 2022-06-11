package com.hanyeop.data.repository.music.local

import com.hanyeop.data.db.MusicDao
import com.hanyeop.data.model.music.MusicEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicLocalDataSourceImpl @Inject constructor(private val musicDao: MusicDao)
    : MusicLocalDataSource {
    override fun insertMusic(music: MusicEntity) = musicDao.insertMusic(music)
    override fun getAllMusic(): Flow<List<MusicEntity>> = musicDao.getAllMusic()
    override fun deleteMusic(id : Int) = musicDao.deleteMusic(id)
    override fun updateMusic(music: MusicEntity) = musicDao.updateMusic(music)
}