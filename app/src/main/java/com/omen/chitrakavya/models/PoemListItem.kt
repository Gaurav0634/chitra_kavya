package com.omen.chitrakavya.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "poem")
data class PoemListItem(
    @PrimaryKey(autoGenerate = true)
    val poemId: Int,
    val author: String,
    val title: String,
    val lines: List<String>
)
