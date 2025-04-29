package com.mantas.bvstatsapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "line_table")
data class Line(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Default value for auto-generation
    val startX: Float,
    val startY: Float,
    val endX: Float,
    val endY: Float,
    val color: Int
)

