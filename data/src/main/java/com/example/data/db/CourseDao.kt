package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: CourseEntity)

    @Query("SELECT * FROM CourseEntity WHERE hasLike = 1")
    suspend fun getLikedCourse(): List<CourseEntity>
}