package com.example.worktimetracker.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.worktimetracker.data.local.Converters
import com.example.worktimetracker.data.local.db.dao.DatabaseDao
import com.example.worktimetracker.data.local.db.entity.NotificationEntity
import com.example.worktimetracker.data.local.db.entity.UserSession

class AppDatabase(
    private val delegate: InternalDatabase,
) : DatabaseDao by delegate.dao {

    fun query(block: AppDatabase.() -> Unit) = with(delegate) {
        queryExecutor.execute {
            block(this@AppDatabase)
        }
    }

    fun transaction(block: AppDatabase.() -> Unit) = with(delegate) {
        transactionExecutor.execute {
            runInTransaction {
                block(this@AppDatabase)
            }
        }
    }

    fun close() = delegate.close()
}

@Database(
    entities = [
        NotificationEntity::class, UserSession::class
    ],
    exportSchema = true,
    version = 3,
)
@TypeConverters(Converters::class)
abstract class InternalDatabase : RoomDatabase() {
    abstract val dao: DatabaseDao

    companion object {
        private const val DB_NAME = "worktimetracker.db"

        fun newInstance(context: Context) =
            AppDatabase(
                delegate = Room
                    .databaseBuilder(context, InternalDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            )

    }
}