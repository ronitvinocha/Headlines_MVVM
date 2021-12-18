package com.practice.headlines.persistance

import androidx.room.*
import io.reactivex.Flowable

@Database(entities = arrayOf(Article::class), version = 1,exportSchema = false)
abstract class AppDatabase:RoomDatabase (){
    abstract fun NewsDescriptionDao(): NewsDescriptionDao
}

@Entity(tableName = "News")
data class Article(
    @ColumnInfo(name = "title")var title: String?,
    @ColumnInfo(name = "description")var description: String?,
    @ColumnInfo(name = "urltoimage")var urltoimage: String?,
    @ColumnInfo(name = "date")var date: String?,
    @ColumnInfo(name = "source")var source: String?
){
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
}

@Dao
interface NewsDescriptionDao {
    @Query("SELECT * FROM News ")
    fun getDescriptions(): Flowable<List<Article>>

    @Insert
    fun insert(news: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotoDescriptions(vararg article: Article)

    @Query("DELETE FROM News")
    fun nukeTable()
}

