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
public final class MovieDao_Impl implements MovieDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMovie;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfMovie;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public MovieDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMovie = new EntityInsertionAdapter<Movie>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `movie`(`mid`,`title`,`directorId`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Movie value) {
        stmt.bindLong(1, value.id);
        if (value.title == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.title);
        }
        stmt.bindLong(3, value.directorId);
      }
    };
    this.__updateAdapterOfMovie = new EntityDeletionOrUpdateAdapter<Movie>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR IGNORE `movie` SET `mid` = ?,`title` = ?,`directorId` = ? WHERE `mid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Movie value) {
        stmt.bindLong(1, value.id);
        if (value.title == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.title);
        }
        stmt.bindLong(3, value.directorId);
        stmt.bindLong(4, value.id);
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM movie";
        return _query;
      }
    };
  }

  @Override
  public void insert(Movie... directors) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMovie.insert(directors);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Movie director) {
    __db.beginTransaction();
    try {
      __updateAdapterOfMovie.handle(director);
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
  public Movie findMovieByTitle(String title) {
    final String _sql = "SELECT * FROM movie WHERE title = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (title == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, title);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("mid");
      final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
      final int _cursorIndexOfDirectorId = _cursor.getColumnIndexOrThrow("directorId");
      final Movie _result;
      if(_cursor.moveToFirst()) {
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        final int _tmpDirectorId;
        _tmpDirectorId = _cursor.getInt(_cursorIndexOfDirectorId);
        _result = new Movie(_tmpTitle,_tmpDirectorId);
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
  public LiveData<List<Movie>> getAllMovies() {
    final String _sql = "SELECT * FROM movie ORDER BY title ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Movie>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<Movie> compute() {
        if (_observer == null) {
          _observer = new Observer("movie") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("mid");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDirectorId = _cursor.getColumnIndexOrThrow("directorId");
          final List<Movie> _result = new ArrayList<Movie>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Movie _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final int _tmpDirectorId;
            _tmpDirectorId = _cursor.getInt(_cursorIndexOfDirectorId);
            _item = new Movie(_tmpTitle,_tmpDirectorId);
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
