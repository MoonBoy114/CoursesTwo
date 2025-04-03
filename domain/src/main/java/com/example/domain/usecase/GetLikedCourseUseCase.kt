package com.example.domain.usecase

import com.example.domain.model.Course
import com.example.domain.repository.CourseRepository

class GetLikedCourseUseCase(private val repository: CourseRepository) {
    suspend operator fun invoke(): List<Course> {
        return repository.getLikeCourse()
    }
}