package com.example.protosuite.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.protosuite.data.db.entities.DataItem
import com.example.protosuite.data.db.entities.NoteItem
import com.example.protosuite.ui.notes.Converters

// Annotates class to be a Room Database with a table (entity) of the Note class
@Database(
    entities = [NoteItem::class, DataItem::class],
    version = 10,
    autoMigrations = [
        AutoMigration(from = 9, to = 10)

                     ],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}