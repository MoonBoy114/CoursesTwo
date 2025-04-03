package com.example.domain.repository

import com.example.domain.model.Course

interface CourseRepository {
    suspend fun getCourses(): List<Course>
    suspend fun toggleLike(course: Course)
    suspend fun getLikeCourse(): List<Course>
}