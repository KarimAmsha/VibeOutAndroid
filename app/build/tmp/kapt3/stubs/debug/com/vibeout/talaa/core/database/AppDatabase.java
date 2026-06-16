package com.vibeout.talaa.core.database;

import androidx.room.*;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&\u00a8\u0006\b"}, d2 = {"Lcom/vibeout/talaa/core/database/AppDatabase;", "Landroidx/room/RoomDatabase;", "<init>", "()V", "cityDao", "Lcom/vibeout/talaa/core/database/CityDao;", "savedPlaceDao", "Lcom/vibeout/talaa/core/database/SavedPlaceDao;", "app_debug"})
@androidx.room.Database(entities = {com.vibeout.talaa.core.database.CityEntity.class, com.vibeout.talaa.core.database.SavedPlaceEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.vibeout.talaa.core.database.CityDao cityDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.vibeout.talaa.core.database.SavedPlaceDao savedPlaceDao();
}