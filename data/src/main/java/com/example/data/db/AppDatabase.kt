package com.example.data.db

import androidx.room.Database


@Database(entities = [CourseEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase {
    abstract fun courseDao(): CourseDao


}