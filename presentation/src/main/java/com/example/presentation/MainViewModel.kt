package com.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Course
import com.example.domain.usecase.GetCourseUseCase
import com.example.domain.usecase.GetLikedCourseUseCase
import com.example.domain.usecase.ToggleLikeCourseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCoursesUseCase: GetCourseUseCase,
    private val toggleLikeUseCase: ToggleLikeCourseUseCase,
    private val getLikedCourseUseCase: GetLikedCourseUseCase
) : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _likedCourse = MutableStateFlow<List<Course>>(emptyList())
    val likedCourse: StateFlow<List<Course>> = _likedCourse.asStateFlow()

    init {
        loadCourses()
        loadLikedCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            _courses.value = getCoursesUseCase()
        }
    }

    private fun loadLikedCourses() {
        viewModelScope.launch {
            _likedCourse.value = getLikedCourseUseCase()
        }
    }

    private fun sortCourses() {
        _courses.value = _courses.value.sortedByDescending { it.publishDate }
    }

    private fun toggleLike(course: Course) {
        viewModelScope.launch {
            toggleLikeUseCase(course)
            _courses.value = _courses.value.map {
                if(it.id == course.id) it.copy(
                    hasLike = !it.hasLike
                ) else it
            }
            _likedCourse.value = getLikedCourseUseCase()
        }
    }
}