package com.example.embeddedroomsample.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.embeddedroomsample.data.*
import com.example.embeddedroomsample.data.dao.CompanyDao

@Database(
    entities = [CompanyEntity::class,LogoEntity::class, Salary::class, EmployeeEntity::class, BranchEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun companyDao(): CompanyDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: AppDatabase

        @JvmStatic
        fun getInstance(context: Context): AppDatabase {
            synchronized(AppDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "company.db"
                ).build()
            }

            return INSTANCE
        }
    }


}