package com.example.unibookapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UniBookDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: UniBookDatabase? = null

        fun getDatabase(context: Context): UniBookDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    UniBookDatabase::class.java,
                    "unibook_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}