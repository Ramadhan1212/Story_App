package com.ipoy.storyapp_v1.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ipoy.storyapp_v1.story.Stories

@Dao
interface StoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(story: List<Stories>)

    @Query("SELECT * FROM story")
    fun getAllStories(): PagingSource<Int, Stories>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}