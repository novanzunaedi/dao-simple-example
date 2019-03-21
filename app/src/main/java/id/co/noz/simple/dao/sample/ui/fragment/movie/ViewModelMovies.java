package id.co.noz.simple.dao.sample.ui.fragment.movie;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import id.co.noz.simple.dao.sample.db.Movie;
import id.co.noz.simple.dao.sample.db.MovieDao;
import id.co.noz.simple.dao.sample.db.MoviesDatabase;

public class ViewModelMovies extends AndroidViewModel {
    private MovieDao movieDao;
    private LiveData<List<Movie>> moviesLiveData;

    public ViewModelMovies(@NonNull Application application) {
        super(application);
        movieDao = MoviesDatabase.getDatabase(application).movieDao();
        moviesLiveData = movieDao.getAllMovies();
    }

    public LiveData<List<Movie>> getMoviesList() {
        return moviesLiveData;
    }

    public void insert(Movie... movies) {
        movieDao.insert(movies);
    }

    public void update(Movie movie) {
        movieDao.update(movie);
    }

    public void deleteAll() {
        movieDao.deleteAll();
    }
}