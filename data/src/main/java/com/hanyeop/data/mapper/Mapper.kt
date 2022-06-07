package com.hanyeop.data.mapper

import com.hanyeop.data.model.music.MusicEntity
import com.hanyeop.data.model.music.MusicResponse
import com.hanyeop.domain.model.music.DomainMusicResponse
import com.hanyeop.domain.model.music.Music

// Domain -> Data
fun mapperToMusicEntity(music: Music): MusicEntity{
    return MusicEntity(
        image = music.image,
        title = music.title,
        artist = music.artist,
        rating = music.rating,
        summary = music.summary,
        content = music.content
    )
}

// Data -> Domain
fun mapperToMusic(musics: List<MusicEntity>): List<Music>{
    return musics.toList().map {
        Music(
            id = it.id,
            image = it.image,
            title = it.title,
            artist = it.artist,
            rating = it.rating,
            summary = it.summary,
            content = it.content,
        )
    }
}

// Data -> Domain
fun mapperToMusicResponse(MusicResponses: MusicResponse): List<DomainMusicResponse>{
    return MusicResponses.channel!!.itemList!!.map {
        DomainMusicResponse(
            title = it.title,
            artist = it.artist!!.name,
            image = it.album!!.image
        )
    }
}