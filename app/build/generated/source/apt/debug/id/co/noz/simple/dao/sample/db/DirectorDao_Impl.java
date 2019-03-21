package id.co.noz.simple.dao.sample.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class DirectorDao_Impl implements DirectorDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfDirector;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfDirector;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public DirectorDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDirector = new EntityInsertionAdapter<Director>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `director`(`did`,`full_name`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Director value) {
        stmt.bindLong(1, value.id);
        if (value.fullName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.fullName);
        }
      }
    };
    this.__updateAdapterOfDirector = new EntityDeletionOrUpdateAdapter<Director>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR IGNORE `director` SET `did` = ?,`full_name` = ? WHERE `did` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Director value) {
        stmt.bindLong(1, value.id);
        if (value.fullName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.fullName);
        }
        stmt.bindLong(3, value.id);
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM director";
        return _query;
      }
    };
  }

  @Override
  public long insert(Director director) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfDirector.insertAndReturnId(director);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(Director... directors) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfDirector.insert(directors);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Director director) {
    __db.beginTransaction();
    try {
      __updateAdapterOfDirector.handle(director);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public Director findDirectorById(int id) {
    final String _sql = "SELECT * FROM director WHERE did = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("did");
      final int _cursorIndexOfFullName = _cursor.getColumnIndexOrThrow("full_name");
      final Director _result;
      if(_cursor.moveToFirst()) {
        final String _tmpFullName;
        _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
        _result = new Director(_tmpFullName);
        _result.id = _cursor.getInt(_cursorIndexOfId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Director findDirectorByName(String fullName) {
    final String _sql = "SELECT * FROM director WHERE full_name = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (fullName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fullName);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("did");
      final int _cursorIndexOfFullName = _cursor.getColumnIndexOrThrow("full_name");
      final Director _result;
      if(_cursor.moveToFirst()) {
        final String _tmpFullName;
        _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
        _result = new Director(_tmpFullName);
        _result.id = _cursor.getInt(_cursorIndexOfId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Director>> getAllDirectors() {
    final String _sql = "SELECT * FROM director ORDER BY full_name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Director>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<Director> compute() {
        if (_observer == null) {
          _observer = new Observer("director") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("did");
          final int _cursorIndexOfFullName = _cursor.getColumnIndexOrThrow("full_name");
          final List<Director> _result = new ArrayList<Director>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Director _item;
            final String _tmpFullName;
            _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            _item = new Director(_tmpFullName);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
