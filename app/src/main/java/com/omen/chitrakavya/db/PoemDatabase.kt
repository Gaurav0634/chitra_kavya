package com.omen.chitrakavya.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.omen.chitrakavya.models.PoemListItem

@Database(entities = [PoemListItem::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PoemDatabase : RoomDatabase() {

    abstract fun poemDao(): PoemDao

    companion object {
        private const val CURRENT_DATABASE_VERSION = 3

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `poem_new` (`poemId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `author` TEXT NOT NULL, `lines` TEXT NOT NULL)")
                database.execSQL("INSERT INTO `poem_new` (`poemId`, `title`, `author`, `lines`) SELECT `poemId`, `title`, `author`, `lines` FROM `poem`")
                database.execSQL("DROP TABLE `poem`")
                database.execSQL("ALTER TABLE `poem_new` RENAME TO `poem`")
            }
        }

        @Volatile
        private var INSTANCE: PoemDatabase? = null

        fun getDatabase(context: Context): PoemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PoemDatabase::class.java,
                    "PoemDB"
                )
                    .addMigrations(MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
