package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourseEntity(
    @PrimaryKey val id: Int,
    val hasLike: Boolean
)