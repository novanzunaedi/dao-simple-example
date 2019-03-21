package id.co.noz.simple.dao.sample.ui.fragment.movie;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.noz.simple.dao.sample.R;
import id.co.noz.simple.dao.sample.db.Movie;
import id.co.noz.simple.dao.sample.ui.fragment.director.ViewModelDirectors;

public class FragmentMoviesList extends Fragment {
    private MoviesListAdapter moviesListAdapter;
    private ViewModelMovies moviesViewModel;
    private Context context;

    public static FragmentMoviesList newInstance() {
        return new FragmentMoviesList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        moviesListAdapter = new MoviesListAdapter(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_movies);
        recyclerView.setAdapter(moviesListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

    private void initData() {
        moviesViewModel = ViewModelProviders.of(this).get(ViewModelMovies.class);
        moviesViewModel.getMoviesList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                moviesListAdapter.setMovieList(movies);
            }
        });
    }

    public void removeData() {
        if (moviesListAdapter != null) {
            moviesViewModel.deleteAll();
        }
    }
}
