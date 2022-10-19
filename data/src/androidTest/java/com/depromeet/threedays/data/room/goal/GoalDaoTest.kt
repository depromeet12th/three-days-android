package com.depromeet.threedays.data.room.goal

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.depromeet.threedays.data.datasource.GoalDataSource
import com.depromeet.threedays.data.room.ThreeDaysDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class GoalDaoTest {
    private lateinit var db: ThreeDaysDatabase
    private lateinit var goalDao: GoalDao
    private lateinit var goalDataSource: GoalDataSource
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ThreeDaysDatabase::class.java).build()
        goalDao = db.goalDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() = testScope.runBlockingTest {
        val goalEntity = GoalEntity(
            title = "title",
        )
        launch { val createdGoalEntity = goalDao.update(goalEntity) }

        launch {
            val goalEntities = goalDao.selectGoals()
            Assert.assertEquals(goalEntities.single().size, 1)
            Assert.assertEquals(goalEntities.single().first().title, goalEntity.title)
        }
        // launch 없을 때
        // java.lang.IllegalStateException: This job has not completed yet

        // launch 있을 때
        // kotlinx.coroutines.test.UncompletedCoroutinesError: Test finished with active jobs: [StandaloneCoroutine{Active}@4904519]
    }
}
