package com.example.unibookapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Book::class, UserBook::class, Review::class], version = 3, exportSchema = false)
abstract class UniBookDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao
    abstract fun userBookDao(): UserBookDao
    abstract fun reviewDao(): ReviewDao

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
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}