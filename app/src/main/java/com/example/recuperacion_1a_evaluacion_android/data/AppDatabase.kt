package com.example.recuperacion_1a_evaluacion_android.data

import android.content.Context
import androidx.room.Database
import androidx.room.*
import com.example.recuperacion_1a_evaluacion_android.data.entity.Libro

@Database(
    entities = [Libro::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract val bookDao: BookDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "app_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}