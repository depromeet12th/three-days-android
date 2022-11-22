package com.depromeet.threedays.data.di

//@InstallIn(SingletonComponent::class)
//@Module
//class LocalDatabaseModule {
//    @Provides
//    @Singleton
//    fun provideUserDatabase(@ApplicationContext context: Context): ThreeDaysDatabase {
//        return Room.databaseBuilder(
//            context,
//            ThreeDaysDatabase::class.java,
//            DATABASE_NAME_THREE_DAYS
//        ).fallbackToDestructiveMigration().build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideGoalDao(threeDaysDatabase: ThreeDaysDatabase): GoalDao {
//        return threeDaysDatabase.goalDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideGoalDataSource(goalDao: GoalDao): HabitDataSource {
//        return RemoteHabitDataSource(goalDao)
//    }
//}
