package id.co.noz.simple.dao.sample;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import id.co.noz.simple.dao.sample.db.MoviesDatabase;
import id.co.noz.simple.dao.sample.ui.fragment.director.FragmentDirectorsList;
import id.co.noz.simple.dao.sample.ui.fragment.director.SaveDialogFragmentDirector;
import id.co.noz.simple.dao.sample.ui.fragment.movie.FragmentMoviesList;
import id.co.noz.simple.dao.sample.ui.fragment.movie.SaveDialogFragmentMovies;

import static id.co.noz.simple.dao.sample.ui.fragment.director.SaveDialogFragmentDirector.TAG_DIALOG_DIRECTOR_SAVE;
import static id.co.noz.simple.dao.sample.ui.fragment.movie.SaveDialogFragmentMovies.TAG_DIALOG_MOVIE_SAVE;

public class MainActivity extends AppCompatActivity {

    private boolean MOVIES_SHOWN = true;
    private Fragment shownFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(getString(R.string.app_name));
        initView();

        if (savedInstanceState == null) {
            showFragment(FragmentMoviesList.newInstance());
        }
    }

    public void setToolbar(@NonNull String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
//        setSupportActionBar(toolbar);
    }

    private void initView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_movies:
                        MOVIES_SHOWN = true;
                        showFragment(FragmentMoviesList.newInstance());
                        return true;
                    case R.id.navigation_directors:
                        MOVIES_SHOWN = false;
                        showFragment(FragmentDirectorsList.newInstance());
                        return true;
                }
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaveDialog();
            }
        });
    }

    private void showFragment(final Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentHolder, fragment);
        fragmentTransaction.commitNow();
        shownFragment = fragment;
    }

    private void showSaveDialog() {
        DialogFragment dialogFragment;
        String tag;
        if (MOVIES_SHOWN) {
            dialogFragment = SaveDialogFragmentMovies.newInstance(null, null);
            tag = TAG_DIALOG_MOVIE_SAVE;
        } else {
            dialogFragment = SaveDialogFragmentDirector.newInstance(null);
            tag = TAG_DIALOG_DIRECTOR_SAVE;
        }

        dialogFragment.show(getSupportFragmentManager(), tag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_delete_list_data) {
            deleteCurrentListData();
            return true;
        } else if (id == R.id.action_re_create_database) {
            reCreateDatabase();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteCurrentListData() {
        if (MOVIES_SHOWN) {
            ((FragmentMoviesList) shownFragment).removeData();
        } else {
            ((FragmentDirectorsList) shownFragment).removeData();
        }
    }

    private void reCreateDatabase() {
        MoviesDatabase.getDatabase(this).clearDb();
    }
}
