package FragmentsClass.MainModulesFragments.Notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import DataBase.SharedPreferencesNames;
import HelperClasses.InformationDialog;

public class NotesFragment extends Fragment {

    ConstraintLayout btnBasicDate, btnAddNote, btnMyNote;

    Fragment fragment = null;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_notes_fragment, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        createListeners();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.information) {
            InformationDialog informationDialog = new InformationDialog();
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_income_daily));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        btnBasicDate=view.findViewById(R.id.btn_basic_date);
        btnAddNote=view.findViewById(R.id.btn_add_note);
        btnMyNote=view.findViewById(R.id.btn_my_note);
    }

    private void createListeners() {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.btn_basic_date:
                {
                    fragment=new BasicDateFragment();
                }break;
                case R.id.btn_add_note:
                {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(SharedPreferencesNames.ToolSharedPreferences.NOTE_OPEN_MODE, 1);
                    editor.apply();
                    fragment = new AddNoteFragment();
                }break;
                case R.id.btn_my_note:
                {
                    fragment = new MyNotesFragment();
                }break;
            }
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        };
        btnBasicDate.setOnClickListener(listener);
        btnAddNote.setOnClickListener(listener);
        btnMyNote.setOnClickListener(listener);
    }
}


