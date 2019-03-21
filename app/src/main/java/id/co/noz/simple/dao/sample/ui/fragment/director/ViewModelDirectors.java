package id.co.noz.simple.dao.sample.ui.fragment.director;

import android.app.Application;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import id.co.noz.simple.dao.sample.db.Director;
import id.co.noz.simple.dao.sample.db.DirectorDao;
import id.co.noz.simple.dao.sample.db.MoviesDatabase;

public class ViewModelDirectors extends AndroidViewModel {
    private DirectorDao directorDao;
    private LiveData<List<Director>> directorsLiveData;

    public ViewModelDirectors(@NonNull Application application) {
        super(application);
        directorDao = MoviesDatabase.getDatabase(application).directorDao();
        directorsLiveData = directorDao.getAllDirectors();
    }

    public LiveData<List<Director>> getDirectorList() {
        return directorsLiveData;
    }

    public void insert(Director... directors) {
        directorDao.insert(directors);
    }

    public void update(Director director) {
        directorDao.update(director);
    }

    public void deleteAll() {
        directorDao.deleteAll();
    }
}



