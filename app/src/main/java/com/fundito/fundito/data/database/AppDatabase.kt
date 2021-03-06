package com.fundito.fundito.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.annotations.SerializedName

/**
 * Created by mj on 29, December, 2019
 */
@Database(entities = [SearchItem::class],version = 1,exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "2"
    }

    abstract fun searchDao() : SearchDao
}

@Entity
data class SearchItem(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @SerializedName("store_idx")
    val storeIdx : Int,

    @SerializedName("name")
    val name : String
)

@Dao
interface SearchDao{

    companion object {
        private const val MAX = 100
    }

    @Transaction
    suspend fun insertNewItem(item : SearchItem) {
        if(findSameItem(item.name) > 0)
            return
        insert(item)
        if(countAll() > MAX)
            deleteTenItems()
    }

    @Insert
    suspend fun insert(item : SearchItem) : Long

    @Delete
    suspend fun delete(item : SearchItem)

    @Query("SELECT COUNT(id) FROM SearchItem WHERE :name == name")
    fun findSameItem(name : String) : Int

    @Query("SELECT * FROM SearchItem ORDER BY id DESC LIMIT :limit")
    fun list(limit : Int = 10) : LiveData<List<SearchItem>>

    @Query("DELETE FROM SearchItem WHERE id IN (SELECT id FROM SearchItem ORDER BY id ASC LIMIT 10)")
    suspend fun deleteTenItems() : Int

    @Query("SELECT COUNT(id) FROM SearchItem")
    suspend fun countAll() : Int

}