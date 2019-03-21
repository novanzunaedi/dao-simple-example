package id.co.noz.simple.dao.sample.db;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Movie.class, Director.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {
    private static MoviesDatabase INSTANCE;
    private static final String DB_NAME = "movies.db";

    public static MoviesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoviesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoviesDatabase.class, DB_NAME)
                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d("MoviesDatabase", "populating with data...");
                                    new PopulateDbAsync(INSTANCE).execute();
                                }
                            })
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public void clearDb() {
        if (INSTANCE != null) {
            new PopulateDbAsync(INSTANCE).execute();
        }
    }

    public abstract MovieDao movieDao();

    public abstract DirectorDao directorDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final MovieDao movieDao;
        private final DirectorDao directorDao;

        public PopulateDbAsync(MoviesDatabase instance) {
            movieDao = instance.movieDao();
            directorDao = instance.directorDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.deleteAll();
            directorDao.deleteAll();

            Director directorOne = new Director("Parto Kusumo");
            Director directorTwo = new Director("Dhani Prasetyo");
            Director directorThree = new Director("Inspectur Ladusing");

            Movie movieOne = new Movie("Makan Makan", (int) directorDao.insert(directorOne));
            final int dIdTwo = (int) directorDao.insert(directorTwo);
            Movie movieTwo = new Movie("Jomblo Tulen", dIdTwo);
            Movie movieThree = new Movie("Istri 3 (Madu 3)", dIdTwo);
            Movie movieFour = new Movie("Misteri Jembatan", (int) directorDao.insert(directorThree));

            movieDao.insert(movieOne, movieTwo, movieThree, movieFour);

            return null;
        }
    }
}
































































