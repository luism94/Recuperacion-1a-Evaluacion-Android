package com.example.recuperacion_1a_evaluacion_android.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import androidx.room.Database
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
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
                            context,
                            AppDatabase::class.java,
                            "libros"
                        )
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                //Con Dao???
                                db.insert(
                                    "libro",
                                    SQLiteDatabase.CONFLICT_ABORT,
                                    contentValuesOf("titulo" to "Ofrenda a la tormenta", "autor" to "Dolores Redondo", "fecha_publicacion" to 2014, "url_portada" to "https://loremflickr.com/320/240", "descripcion_sinopsis" to "sinopsis")
                                )
                                db.insert(
                                    "libro",
                                    SQLiteDatabase.CONFLICT_ABORT,
                                    contentValuesOf("titulo" to "El Código DaVinci", "autor" to "Dan Brown", "fecha_publicacion" to 2010, "url_portada" to "https://loremflickr.com/320/240", "descripcion_sinopsis" to "sinopsis")
                                )
                                db.insert(
                                    "libro",
                                    SQLiteDatabase.CONFLICT_ABORT,
                                    contentValuesOf("titulo" to "Ángeles y Demonios", "autor" to "Dan Brown", "fecha_publicacion" to 2012, "url_portada" to "https://loremflickr.com/320/240", "descripcion_sinopsis" to "sinopsis")
                                )
                            }
                        })
//                        .createFromAsset("database/libros.db")  //Para recuperar la bbdd guardada en local
                        .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}