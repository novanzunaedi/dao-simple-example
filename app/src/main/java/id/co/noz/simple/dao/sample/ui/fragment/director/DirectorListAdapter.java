package id.co.noz.simple.dao.sample.ui.fragment.director;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import id.co.noz.simple.dao.sample.R;
import id.co.noz.simple.dao.sample.db.Director;

import static id.co.noz.simple.dao.sample.ui.fragment.director.SaveDialogFragmentDirector.TAG_DIALOG_DIRECTOR_SAVE;

public class DirectorListAdapter extends RecyclerView.Adapter<DirectorListAdapter.DirectorsViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Director> directorList;
    private Context context;

    public DirectorListAdapter(Context context){
        this.layoutInflater = LayoutInflater.from(context);
        this.context =context;
    }

    public void setDirectorList(List<Director> directorList){
        this.directorList = directorList;
        notifyDataSetChanged();
    }
    @androidx.annotation.NonNull
    @Override
    public DirectorListAdapter.DirectorsViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        final View itemView = layoutInflater.inflate(R.layout.item_list_director, parent, false);

        return new DirectorListAdapter.DirectorsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull DirectorListAdapter.DirectorsViewHolder holder, int position) {
        if(directorList == null){
            return;
        }

        final Director director = directorList.get(position);
        if(director != null){
            holder.directorText.setText(director.fullName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment dialogFragment = SaveDialogFragmentDirector.newInstance(director.fullName);
                    dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), TAG_DIALOG_DIRECTOR_SAVE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (directorList == null){
            return 0;
        } else {
            return directorList.size();
        }
    }

    static class DirectorsViewHolder extends RecyclerView.ViewHolder{
        private TextView directorText;


        public DirectorsViewHolder(@androidx.annotation.NonNull View itemView) {
            super(itemView);
            directorText = itemView.findViewById(R.id.tvDirector);
        }
    }
}




