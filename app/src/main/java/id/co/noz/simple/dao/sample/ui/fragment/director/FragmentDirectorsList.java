package id.co.noz.simple.dao.sample.ui.fragment.director;

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
import id.co.noz.simple.dao.sample.db.Director;

public class FragmentDirectorsList extends Fragment {
    private DirectorListAdapter directorsListAdapter;
    private ViewModelDirectors directorsViewModel;
    private Context context;

    public static FragmentDirectorsList newInstance() {
        return new FragmentDirectorsList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        directorsListAdapter = new DirectorListAdapter(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directors, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_directors);
        recyclerView.setAdapter(directorsListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    private void initData() {
        directorsViewModel = ViewModelProviders.of(this).get(ViewModelDirectors.class);
        directorsViewModel.getDirectorList().observe(this, new Observer<List<Director>>() {
            @Override
            public void onChanged(@Nullable List<Director> directors) {
                directorsListAdapter.setDirectorList(directors);
            }
        });
    }

    public void removeData() {
        if (directorsViewModel != null) {
            directorsViewModel.deleteAll();
        }
    }
}


