package com.vibeout.talaa.core.database;

import androidx.annotation.NonNull;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile CityDao _cityDao;

  private volatile SavedPlaceDao _savedPlaceDao;

  @Override
  @NonNull
  protected RoomOpenDelegate createOpenDelegate() {
    final RoomOpenDelegate _openDelegate = new RoomOpenDelegate(1, "4b3ab823e1978e6751e4eb3c5db9a6d5", "7fed2474e884bad0fdc4afad8c7fbbe4") {
      @Override
      public void createAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `cities` (`id` TEXT NOT NULL, `nameAr` TEXT NOT NULL, `nameEn` TEXT NOT NULL, `nameTr` TEXT NOT NULL, `countryCode` TEXT NOT NULL, `timezone` TEXT NOT NULL, `latitude` REAL, `longitude` REAL, `status` TEXT NOT NULL, PRIMARY KEY(`id`))");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `saved_place_ids` (`placeId` TEXT NOT NULL, PRIMARY KEY(`placeId`))");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4b3ab823e1978e6751e4eb3c5db9a6d5')");
      }

      @Override
      public void dropAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `cities`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `saved_place_ids`");
      }

      @Override
      public void onCreate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      public void onOpen(@NonNull final SQLiteConnection connection) {
        internalInitInvalidationTracker(connection);
      }

      @Override
      public void onPreMigrate(@NonNull final SQLiteConnection connection) {
        DBUtil.dropFtsSyncTriggers(connection);
      }

      @Override
      public void onPostMigrate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      @NonNull
      public RoomOpenDelegate.ValidationResult onValidateSchema(
          @NonNull final SQLiteConnection connection) {
        final Map<String, TableInfo.Column> _columnsCities = new HashMap<String, TableInfo.Column>(9);
        _columnsCities.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCities.put("nameAr", new TableInfo.Column("nameAr", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCities.put("nameEn", new TableInfo.Column("nameEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCities.put("nameTr", new TableInfo.Column("nameTr", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCities.put("countryCode", new TableInfo.Column("countryCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCities.put("timezone", new TableInfo.Column("timezone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCities.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCities.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCities.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysCities = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesCities = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCities = new TableInfo("cities", _columnsCities, _foreignKeysCities, _indicesCities);
        final TableInfo _existingCities = TableInfo.read(connection, "cities");
        if (!_infoCities.equals(_existingCities)) {
          return new RoomOpenDelegate.ValidationResult(false, "cities(com.vibeout.talaa.core.database.CityEntity).\n"
                  + " Expected:\n" + _infoCities + "\n"
                  + " Found:\n" + _existingCities);
        }
        final Map<String, TableInfo.Column> _columnsSavedPlaceIds = new HashMap<String, TableInfo.Column>(1);
        _columnsSavedPlaceIds.put("placeId", new TableInfo.Column("placeId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysSavedPlaceIds = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesSavedPlaceIds = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSavedPlaceIds = new TableInfo("saved_place_ids", _columnsSavedPlaceIds, _foreignKeysSavedPlaceIds, _indicesSavedPlaceIds);
        final TableInfo _existingSavedPlaceIds = TableInfo.read(connection, "saved_place_ids");
        if (!_infoSavedPlaceIds.equals(_existingSavedPlaceIds)) {
          return new RoomOpenDelegate.ValidationResult(false, "saved_place_ids(com.vibeout.talaa.core.database.SavedPlaceEntity).\n"
                  + " Expected:\n" + _infoSavedPlaceIds + "\n"
                  + " Found:\n" + _existingSavedPlaceIds);
        }
        return new RoomOpenDelegate.ValidationResult(true, null);
      }
    };
    return _openDelegate;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final Map<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final Map<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "cities", "saved_place_ids");
  }

  @Override
  public void clearAllTables() {
    super.performClear(false, "cities", "saved_place_ids");
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final Map<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CityDao.class, CityDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SavedPlaceDao.class, SavedPlaceDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final Set<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public CityDao cityDao() {
    if (_cityDao != null) {
      return _cityDao;
    } else {
      synchronized(this) {
        if(_cityDao == null) {
          _cityDao = new CityDao_Impl(this);
        }
        return _cityDao;
      }
    }
  }

  @Override
  public SavedPlaceDao savedPlaceDao() {
    if (_savedPlaceDao != null) {
      return _savedPlaceDao;
    } else {
      synchronized(this) {
        if(_savedPlaceDao == null) {
          _savedPlaceDao = new SavedPlaceDao_Impl(this);
        }
        return _savedPlaceDao;
      }
    }
  }
}
