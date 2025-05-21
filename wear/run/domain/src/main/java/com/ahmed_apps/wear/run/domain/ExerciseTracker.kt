package com.ahmed_apps.wear.run.domain

import com.ahmed_apps.core.domian.util.EmptyResult
import com.ahmed_apps.core.domian.util.Error
import kotlinx.coroutines.flow.Flow

/**
 * @author Ahmed Guedmioui
 */
interface ExerciseTracker {
    val heartRate: Flow<Int>
    suspend fun isHeartRateTrackingSupported(): Boolean
    suspend fun prepareExercise(): EmptyResult<ExerciseError>
    suspend fun startExercise(): EmptyResult<ExerciseError>
    suspend fun resumeExercise(): EmptyResult<ExerciseError>
    suspend fun pauseExercise(): EmptyResult<ExerciseError>
    suspend fun stopExercise(): EmptyResult<ExerciseError>
}

enum class ExerciseError: Error {
    TRACKING_NOT_SUPPORTED,
    ONGOING_OWN_EXERCISE,
    ONGOING_OTHER_EXERCISE,
    EXERCISE_ALREADY_ENDED,
    UNKNOWN
}