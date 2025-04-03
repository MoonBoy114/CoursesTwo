package com.example.data.repositoryImpl

import com.example.data.db.CourseDao
import com.example.data.db.CourseEntity
import com.example.data.network.ApiService
import com.example.data.network.CourseResponse
import com.example.domain.model.Course
import com.example.domain.repository.CourseRepository
import com.google.gson.Gson

class CourseRepositoryImpl(
    private val apiService: ApiService,
    private val courseDao: CourseDao
) : CourseRepository {

    private val gson = Gson()

    override suspend fun getCourses(): List<Course> {
        val response = apiService.downloadCourse()
        val jsonBytes = response.bytes()
        val json = String(jsonBytes, charset("UTF-8"))
        val courseResponse = gson.fromJson(json, CourseResponse::class.java)
        val courses = courseResponse.courses
        val likedCourse = courseDao.getLikedCourse().associateBy { it.id }
        return courses.map { it.copy(hasLike = likedCourse[it.id]?.hasLike ?: it.hasLike) }
    }

    override suspend fun toggleLike(course: Course) {
        courseDao.insert(CourseEntity(course.id, !course.hasLike))
    }
    override suspend fun getLikeCourse(): List<Course> {
        val response = apiService.downloadCourse()
        val jsonBytes = response.bytes()
        val json = String(jsonBytes, charset("UTF-8"))
        val courseResponse = gson.fromJson(json, CourseResponse::class.java)
        val courses = courseResponse.courses
        val likedCourse = courseDao.getLikedCourse().associateBy { it.id }
        return courses.filter { likedCourse[it.id]?.hasLike == true }
    }


}