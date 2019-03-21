package id.co.noz.simple.dao.sample.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class MoviesDatabase_Impl extends MoviesDatabase {
  private volatile MovieDao _movieDao;

  private volatile DirectorDao _directorDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `movie` (`mid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `directorId` INTEGER NOT NULL, FOREIGN KEY(`directorId`) REFERENCES `director`(`did`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE  INDEX `index_movie_title` ON `movie` (`title`)");
        _db.execSQL("CREATE  INDEX `index_movie_directorId` ON `movie` (`directorId`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `director` (`did` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `full_name` TEXT NOT NULL)");
        _db.execSQL("CREATE UNIQUE INDEX `index_director_full_name` ON `director` (`full_name`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"627455825d506754f171b32a983cfc02\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `movie`");
        _db.execSQL("DROP TABLE IF EXISTS `director`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsMovie = new HashMap<String, TableInfo.Column>(3);
        _columnsMovie.put("mid", new TableInfo.Column("mid", "INTEGER", true, 1));
        _columnsMovie.put("title", new TableInfo.Column("title", "TEXT", true, 0));
        _columnsMovie.put("directorId", new TableInfo.Column("directorId", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMovie = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMovie.add(new TableInfo.ForeignKey("director", "CASCADE", "NO ACTION",Arrays.asList("directorId"), Arrays.asList("did")));
        final HashSet<TableInfo.Index> _indicesMovie = new HashSet<TableInfo.Index>(2);
        _indicesMovie.add(new TableInfo.Index("index_movie_title", false, Arrays.asList("title")));
        _indicesMovie.add(new TableInfo.Index("index_movie_directorId", false, Arrays.asList("directorId")));
        final TableInfo _infoMovie = new TableInfo("movie", _columnsMovie, _foreignKeysMovie, _indicesMovie);
        final TableInfo _existingMovie = TableInfo.read(_db, "movie");
        if (! _infoMovie.equals(_existingMovie)) {
          throw new IllegalStateException("Migration didn't properly handle movie(id.co.noz.simple.dao.sample.db.Movie).\n"
                  + " Expected:\n" + _infoMovie + "\n"
                  + " Found:\n" + _existingMovie);
        }
        final HashMap<String, TableInfo.Column> _columnsDirector = new HashMap<String, TableInfo.Column>(2);
        _columnsDirector.put("did", new TableInfo.Column("did", "INTEGER", true, 1));
        _columnsDirector.put("full_name", new TableInfo.Column("full_name", "TEXT", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDirector = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDirector = new HashSet<TableInfo.Index>(1);
        _indicesDirector.add(new TableInfo.Index("index_director_full_name", true, Arrays.asList("full_name")));
        final TableInfo _infoDirector = new TableInfo("director", _columnsDirector, _foreignKeysDirector, _indicesDirector);
        final TableInfo _existingDirector = TableInfo.read(_db, "director");
        if (! _infoDirector.equals(_existingDirector)) {
          throw new IllegalStateException("Migration didn't properly handle director(id.co.noz.simple.dao.sample.db.Director).\n"
                  + " Expected:\n" + _infoDirector + "\n"
                  + " Found:\n" + _existingDirector);
        }
      }
    }, "627455825d506754f171b32a983cfc02", "b54788198efea4a644d83b8e8aad1cd4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "movie","director");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `movie`");
      _db.execSQL("DELETE FROM `director`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public MovieDao movieDao() {
    if (_movieDao != null) {
      return _movieDao;
    } else {
      synchronized(this) {
        if(_movieDao == null) {
          _movieDao = new MovieDao_Impl(this);
        }
        return _movieDao;
      }
    }
  }

  @Override
  public DirectorDao directorDao() {
    if (_directorDao != null) {
      return _directorDao;
    } else {
      synchronized(this) {
        if(_directorDao == null) {
          _directorDao = new DirectorDao_Impl(this);
        }
        return _directorDao;
      }
    }
  }
}
