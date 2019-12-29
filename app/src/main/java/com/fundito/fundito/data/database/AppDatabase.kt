package com.fundito.fundito.data.database

import androidx.room.*

/**
 * Created by mj on 29, December, 2019
 */
@Database(entities = [SearchItem::class],version = 1,exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "1"
    }

    abstract fun searchDao() : SearchDao
}

@Entity
data class SearchItem(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String
)

@Dao
interface SearchDao{

    companion object {
        private const val MAX = 100
    }

    @Transaction
    suspend fun insertNewItem(item : SearchItem) {
        insert(item)
        if(countAll() > MAX)
            deleteTenItems()
    }

    @Insert
    suspend fun insert(item : SearchItem) : Long

    @Delete
    suspend fun delete(item : SearchItem) : Long

    @Query("SELECT * FROM SearchItem ORDER BY id DESC LIMIT :limit")
    suspend fun list(limit : Int = 10) : List<SearchItem>

    @Query("DELETE FROM SearchItem ORDER BY id ASC LIMIT 10")
    suspend fun deleteTenItems() : List<Long>

    @Query("SELECT COUNT(id) FROM SearchItem")
    suspend fun countAll() : Int

}