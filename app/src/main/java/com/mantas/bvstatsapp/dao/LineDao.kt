package com.mantas.bvstatsapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mantas.bvstatsapp.entity.Line

@Dao
interface LineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(line: Line)

    @Update
    fun update(line: Line)

    @Delete
    fun delete(line: Line)

    @Query("SELECT * FROM line_table ORDER BY id ASC")
    fun getAllLines(): LiveData<List<Line>>

    @Query("SELECT * FROM line_table ORDER BY id ASC")
    fun getAllLinesDirect(): List<Line>

    @Query("DELETE FROM line_table")
    fun deleteAll(): Int // Returns the number of rows deleted
}