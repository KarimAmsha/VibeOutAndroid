package com.vibeout.talaa.core.database;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
import java.lang.Double;
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
public final class CityDao_Impl implements CityDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<CityEntity> __insertAdapterOfCityEntity;

  public CityDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfCityEntity = new EntityInsertAdapter<CityEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cities` (`id`,`nameAr`,`nameEn`,`nameTr`,`countryCode`,`timezone`,`latitude`,`longitude`,`status`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final CityEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getId());
        }
        if (entity.getNameAr() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getNameAr());
        }
        if (entity.getNameEn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getNameEn());
        }
        if (entity.getNameTr() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getNameTr());
        }
        if (entity.getCountryCode() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getCountryCode());
        }
        if (entity.getTimezone() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getTimezone());
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getLongitude());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getStatus());
        }
      }
    };
  }

  @Override
  public Object upsertAll(final List<CityEntity> items,
      final Continuation<? super Unit> $completion) {
    if (items == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfCityEntity.insert(_connection, items);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Flow<List<CityEntity>> observeAll() {
    final String _sql = "SELECT * FROM cities ORDER BY nameEn";
    return FlowUtil.createFlow(__db, false, new String[] {"cities"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNameAr = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameAr");
        final int _columnIndexOfNameEn = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEn");
        final int _columnIndexOfNameTr = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameTr");
        final int _columnIndexOfCountryCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "countryCode");
        final int _columnIndexOfTimezone = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timezone");
        final int _columnIndexOfLatitude = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "latitude");
        final int _columnIndexOfLongitude = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "longitude");
        final int _columnIndexOfStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "status");
        final List<CityEntity> _result = new ArrayList<CityEntity>();
        while (_stmt.step()) {
          final CityEntity _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpNameAr;
          if (_stmt.isNull(_columnIndexOfNameAr)) {
            _tmpNameAr = null;
          } else {
            _tmpNameAr = _stmt.getText(_columnIndexOfNameAr);
          }
          final String _tmpNameEn;
          if (_stmt.isNull(_columnIndexOfNameEn)) {
            _tmpNameEn = null;
          } else {
            _tmpNameEn = _stmt.getText(_columnIndexOfNameEn);
          }
          final String _tmpNameTr;
          if (_stmt.isNull(_columnIndexOfNameTr)) {
            _tmpNameTr = null;
          } else {
            _tmpNameTr = _stmt.getText(_columnIndexOfNameTr);
          }
          final String _tmpCountryCode;
          if (_stmt.isNull(_columnIndexOfCountryCode)) {
            _tmpCountryCode = null;
          } else {
            _tmpCountryCode = _stmt.getText(_columnIndexOfCountryCode);
          }
          final String _tmpTimezone;
          if (_stmt.isNull(_columnIndexOfTimezone)) {
            _tmpTimezone = null;
          } else {
            _tmpTimezone = _stmt.getText(_columnIndexOfTimezone);
          }
          final Double _tmpLatitude;
          if (_stmt.isNull(_columnIndexOfLatitude)) {
            _tmpLatitude = null;
          } else {
            _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude);
          }
          final Double _tmpLongitude;
          if (_stmt.isNull(_columnIndexOfLongitude)) {
            _tmpLongitude = null;
          } else {
            _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude);
          }
          final String _tmpStatus;
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmpStatus = null;
          } else {
            _tmpStatus = _stmt.getText(_columnIndexOfStatus);
          }
          _item = new CityEntity(_tmpId,_tmpNameAr,_tmpNameEn,_tmpNameTr,_tmpCountryCode,_tmpTimezone,_tmpLatitude,_tmpLongitude,_tmpStatus);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getAll(final Continuation<? super List<CityEntity>> $completion) {
    final String _sql = "SELECT * FROM cities ORDER BY nameEn";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNameAr = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameAr");
        final int _columnIndexOfNameEn = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEn");
        final int _columnIndexOfNameTr = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameTr");
        final int _columnIndexOfCountryCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "countryCode");
        final int _columnIndexOfTimezone = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timezone");
        final int _columnIndexOfLatitude = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "latitude");
        final int _columnIndexOfLongitude = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "longitude");
        final int _columnIndexOfStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "status");
        final List<CityEntity> _result = new ArrayList<CityEntity>();
        while (_stmt.step()) {
          final CityEntity _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpNameAr;
          if (_stmt.isNull(_columnIndexOfNameAr)) {
            _tmpNameAr = null;
          } else {
            _tmpNameAr = _stmt.getText(_columnIndexOfNameAr);
          }
          final String _tmpNameEn;
          if (_stmt.isNull(_columnIndexOfNameEn)) {
            _tmpNameEn = null;
          } else {
            _tmpNameEn = _stmt.getText(_columnIndexOfNameEn);
          }
          final String _tmpNameTr;
          if (_stmt.isNull(_columnIndexOfNameTr)) {
            _tmpNameTr = null;
          } else {
            _tmpNameTr = _stmt.getText(_columnIndexOfNameTr);
          }
          final String _tmpCountryCode;
          if (_stmt.isNull(_columnIndexOfCountryCode)) {
            _tmpCountryCode = null;
          } else {
            _tmpCountryCode = _stmt.getText(_columnIndexOfCountryCode);
          }
          final String _tmpTimezone;
          if (_stmt.isNull(_columnIndexOfTimezone)) {
            _tmpTimezone = null;
          } else {
            _tmpTimezone = _stmt.getText(_columnIndexOfTimezone);
          }
          final Double _tmpLatitude;
          if (_stmt.isNull(_columnIndexOfLatitude)) {
            _tmpLatitude = null;
          } else {
            _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude);
          }
          final Double _tmpLongitude;
          if (_stmt.isNull(_columnIndexOfLongitude)) {
            _tmpLongitude = null;
          } else {
            _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude);
          }
          final String _tmpStatus;
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmpStatus = null;
          } else {
            _tmpStatus = _stmt.getText(_columnIndexOfStatus);
          }
          _item = new CityEntity(_tmpId,_tmpNameAr,_tmpNameEn,_tmpNameTr,_tmpCountryCode,_tmpTimezone,_tmpLatitude,_tmpLongitude,_tmpStatus);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object clear(final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM cities";
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
