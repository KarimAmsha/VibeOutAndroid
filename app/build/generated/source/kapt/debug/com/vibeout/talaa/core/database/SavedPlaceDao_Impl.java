package com.vibeout.talaa.core.database;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class SavedPlaceDao_Impl implements SavedPlaceDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<SavedPlaceEntity> __insertAdapterOfSavedPlaceEntity;

  public SavedPlaceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfSavedPlaceEntity = new EntityInsertAdapter<SavedPlaceEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `saved_place_ids` (`placeId`) VALUES (?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final SavedPlaceEntity entity) {
        if (entity.getPlaceId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getPlaceId());
        }
      }
    };
  }

  @Override
  public Object save(final SavedPlaceEntity item, final Continuation<? super Unit> $completion) {
    if (item == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfSavedPlaceEntity.insert(_connection, item);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Flow<List<String>> observeIds() {
    final String _sql = "SELECT placeId FROM saved_place_ids";
    return FlowUtil.createFlow(__db, false, new String[] {"saved_place_ids"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final List<String> _result = new ArrayList<String>();
        while (_stmt.step()) {
          final String _item;
          if (_stmt.isNull(0)) {
            _item = null;
          } else {
            _item = _stmt.getText(0);
          }
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object remove(final String id, final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM saved_place_ids WHERE placeId = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object clear(final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM saved_place_ids";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
