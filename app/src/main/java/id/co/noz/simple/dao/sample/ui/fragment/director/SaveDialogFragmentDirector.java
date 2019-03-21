package id.co.noz.simple.dao.sample.ui.fragment.director;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import id.co.noz.simple.dao.sample.R;
import id.co.noz.simple.dao.sample.db.Director;
import id.co.noz.simple.dao.sample.db.DirectorDao;
import id.co.noz.simple.dao.sample.db.MoviesDatabase;

public class SaveDialogFragmentDirector extends DialogFragment {
    private Context context;
    private String directorFullNameExtra;

    private static final String EXTRA_DIRECTOR_FULL_NAME = "director_full_name";
    public static final String TAG_DIALOG_DIRECTOR_SAVE = "dialog_director_save";

    public static SaveDialogFragmentDirector newInstance(String directorFullName) {
        SaveDialogFragmentDirector fragment = new SaveDialogFragmentDirector();

        Bundle args = new Bundle();
        args.putString(EXTRA_DIRECTOR_FULL_NAME, directorFullName);
        fragment.setArguments(args);

        return fragment;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        directorFullNameExtra = args.getString(EXTRA_DIRECTOR_FULL_NAME);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_director, null);
        final EditText directorEditText = view.findViewById(R.id.etDirectorFullName);
        if(directorFullNameExtra != null){
            directorEditText.setText(directorFullNameExtra);
            directorEditText.setSelection(directorFullNameExtra.length());
        }

        alertDialogBuilder.setView(view)
                .setTitle(getString(R.string.dialog_director_title))
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveDirector(directorEditText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return alertDialogBuilder.create();
    }

    private void saveDirector(String fullName){
        if(TextUtils.isEmpty(fullName)){
            return;
        }

        DirectorDao directorDao = MoviesDatabase.getDatabase(context).directorDao();

        if(directorFullNameExtra != null){
            Director directorToUpdate = directorDao.findDirectorByName(directorFullNameExtra);
            if(directorToUpdate != null){
                if(!directorToUpdate.fullName.equals(fullName)){
                    directorToUpdate.fullName = fullName;
                    directorDao.update(directorToUpdate);
                }
            }
        } else {
            directorDao.insert(new Director(fullName));
        }
    }

}
























































