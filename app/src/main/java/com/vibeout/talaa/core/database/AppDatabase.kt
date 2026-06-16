package com.vibeout.talaa.core.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey val id: String,
    val nameAr: String,
    val nameEn: String,
    val nameTr: String,
    val countryCode: String,
    val timezone: String,
    val latitude: Double?,
    val longitude: Double?,
    val status: String,
)

@Entity(tableName = "saved_place_ids")
data class SavedPlaceEntity(@PrimaryKey val placeId: String)

@Dao
interface CityDao {
    @Query("SELECT * FROM cities ORDER BY nameEn") fun observeAll(): Flow<List<CityEntity>>
    @Query("SELECT * FROM cities ORDER BY nameEn") suspend fun getAll(): List<CityEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsertAll(items: List<CityEntity>)
    @Query("DELETE FROM cities") suspend fun clear()
}

@Dao
interface SavedPlaceDao {
    @Query("SELECT placeId FROM saved_place_ids") fun observeIds(): Flow<List<String>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun save(item: SavedPlaceEntity)
    @Query("DELETE FROM saved_place_ids WHERE placeId = :id") suspend fun remove(id: String)
    @Query("DELETE FROM saved_place_ids") suspend fun clear()
}

@Database(entities = [CityEntity::class, SavedPlaceEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun savedPlaceDao(): SavedPlaceDao
}
