package com.example.testapp.activities.data

import android.content.Context
import android.util.Log
import androidx.room.Room

object DatabaseProvider {
    private var db: AppDatabase? = null

    fun getDatabase(context: Context?): AppDatabase {
        Log.d("DatabaseProvider", "DatabaseProvider:$context")
        if (db == null) {
            val ctx = context ?: throw IllegalArgumentException("Context must not be null")
            db = Room.databaseBuilder(
                ctx.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
        return db!!
    }
}
