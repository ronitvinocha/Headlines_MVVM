package com.practice.headlines.persistance
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Database(entities = arrayOf(Article::class), version = 1,exportSchema = false)
abstract class AppDatabase:RoomDatabase (){
    abstract fun NewsDescriptionDao(): NewsDescriptionDao
}

@Entity
data class Article(
    val sourceId    : String?,
    val sourceName  : String?,
    val author      : String?,
    val title       : String?,
    val description : String?,
    val url         : String?,
    val urlToImage  : String?,
    val publishedAt : String?,
    val content     : String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int=0
}

@Dao
interface NewsDescriptionDao {
    @Query("SELECT * FROM Article")
    fun getArticles(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(vararg article: Article):List<Long>

    @Query("DELETE FROM Article WHERE id = :id")
    suspend fun delete(id:Int)
}

