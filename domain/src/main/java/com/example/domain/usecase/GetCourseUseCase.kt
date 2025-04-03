package com.example.domain.usecase

import com.example.domain.model.Course
import com.example.domain.repository.CourseRepository

class GetCourseUseCase(private val repository: CourseRepository) {
    suspend operator fun invoke(): List<Course> = repository.getCourses()


}