package FragmentsClass.MainModulesFragments.Notes;

import static HelperClasses.ToolClass.generateStringDate;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import DataBase.SharedPreferencesNames;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class AddNoteFragment extends Fragment {

    private Context context;
    private ImageView image;
    private TextView title;
    private TextInputEditText howTitle, howDate, howDescribe;
    private Button cancelButton, acceptButton;
    private ImageView editDateButton, addImageButton;

    private int notesMode, id, drawable=0;
    private String calendarDate;
    private boolean click=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_add_note, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        loadData();
        createListeners();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    @SuppressLint("NonConstantResourceId")
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
        image=view.findViewById(R.id.image);
        title=view.findViewById(R.id.title);
        howTitle=view.findViewById(R.id.how_title);
        howDate=view.findViewById(R.id.how_date);
        howDescribe=view.findViewById(R.id.how_describe);
        cancelButton=view.findViewById(R.id.cancel_button);
        acceptButton=view.findViewById(R.id.accept_button);
        editDateButton=view.findViewById(R.id.edit_date_button);
        addImageButton=view.findViewById(R.id.add_image_button);

        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(SharedPreferencesNames.ToolSharedPreferences.POSITION_OF_NOTE_RV, 0);
        notesMode = sharedPreferences.getInt(SharedPreferencesNames.ToolSharedPreferences.NOTE_OPEN_MODE, 1);

    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        if(notesMode==0)
        {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            Cursor k = dataBaseHelper.getSpecifyNotesValues(id);
            k.moveToFirst();
            title.setText("Edycja Notatki");
            image.setImageResource(k.getInt(k.getColumnIndexOrThrow(DataBaseNames.NotesItem.COLUMN_IMAGE)));
            howTitle.setText(k.getString(k.getColumnIndexOrThrow(DataBaseNames.NotesItem.COLUMN_TITLE)));
            howDate.setText(k.getString(k.getColumnIndexOrThrow(DataBaseNames.NotesItem.COLUMN_DATE)));
            howDescribe.setText(k.getString(k.getColumnIndexOrThrow(DataBaseNames.NotesItem.COLUMN_DESCRIBE)));
            addImageButton.setImageResource(R.drawable.icon_edit);
            drawable=k.getInt(k.getColumnIndexOrThrow(DataBaseNames.NotesItem.COLUMN_IMAGE));


        }
    }

    private void createListeners() {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id = v.getId();
            switch (id) {
                case R.id.accept_button: {
                    validateData();
                }
                break;
                case R.id.cancel_button: {
                    ShowToast toast = new ShowToast();
                    if (notesMode == 0)
                        toast.showErrorToast(context, "UWAGA!\n" + "  Przerwałeś proces edycji notatki!", R.drawable.icon_edit);
                    else if (notesMode == 1)
                        toast.showErrorToast(context, "UWAGA!\n" + "  Przerwałeś proces dodawania notatki!", R.drawable.icon_plus);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
                }
                break;
            }
        };
        acceptButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);

        editDateButton.setOnClickListener(v -> openEditDataDialog());
        addImageButton.setOnClickListener(v -> openAddImageDialog());


    }

    private void validateData() {
        ShowToast toast = new ShowToast();
        if(Objects.requireNonNull(howTitle.getText()).toString().compareTo("")==0 || Objects.requireNonNull(howDate.getText()).toString().compareTo("")==0 ||
                Objects.requireNonNull(howDescribe.getText()).toString().compareTo("")==0)
            toast.showErrorToast(context, "BŁĄD DANYCH!\n"+"  Uzupełnij wszystkie pola!", R.drawable.icon_information);
        else {
            if(drawable==0)
                toast.showErrorToast(context, "BRAK OBRAZKA!\n"+"  Wybierz obrazek przyciskiem +", R.drawable.icon_information);
            else {
                if (ToolClass.checkValidateData(howDate.getText().toString()))
                    if (ToolClass.checkValidateYear(howDate.getText().toString()))
                        addOrEditItem();
                    else
                        toast.showErrorToast(context, "Podaj poprawny rok!\n" + "  Mamy aktualnie " + ToolClass.getActualYear() + " rok!", R.drawable.icon_calendar);
                else
                    toast.showErrorToast(context, "Błędny format daty!\n" + "  [dd.mm.rrrr]", R.drawable.icon_calendar);
            }
        }
    }

    private void addOrEditItem() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        ShowToast toast = new ShowToast();
        switch (notesMode)
        {
            case 0:
            {
                dataBaseHelper.updateNote(id, Objects.requireNonNull(howTitle.getText()).toString(), Objects.requireNonNull(howDate.getText()).toString(), Objects.requireNonNull(howDescribe.getText()).toString(), drawable);
                toast.showSuccessfulToast(context, "SUKCES\n" + "  Pomyślnie edytowałeś notatkę!");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
            }break;
            case 1:
            {
                dataBaseHelper.addNote(Objects.requireNonNull(howTitle.getText()).toString(), Objects.requireNonNull(howDate.getText()).toString(), Objects.requireNonNull(howDescribe.getText()).toString(),drawable);
                toast.showSuccessfulToast(context, "SUKCES\n" + "  Pomyślnie dodałeś nową notatke!");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
            }break;
        }
    }

    private void openEditDataDialog() {
        Dialog editDataDialog = new Dialog(context);
        editDataDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        editDataDialog.setContentView(R.layout.dialog_change_date);
        editDataDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editDataDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        editDataDialog.show();
        createDialogListeners(editDataDialog);
    }

    private void createDialogListeners(Dialog editDataDialog) {
        CalendarView calendar;
        Button btnAccept, btnCancel;


        calendar=editDataDialog.findViewById(R.id.calendar);
        btnAccept=editDataDialog.findViewById(R.id.btn_accept);
        btnCancel=editDataDialog.findViewById(R.id.btn_cancel);

        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.btn_accept:
                {
                    howDate.setText(calendarDate);
                    editDataDialog.dismiss();
                }break;
                case R.id.btn_cancel:
                {
                    editDataDialog.dismiss();
                }break;
            }
        };
        btnAccept.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> calendarDate=generateStringDate(dayOfMonth,month,year));
    }

    private void openAddImageDialog() {
        Dialog addImageDialog = new Dialog(context);
        addImageDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        addImageDialog.setContentView(R.layout.dialog_chose_image);
        addImageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addImageDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        addImageDialog.show();
        createDialogListenerss(addImageDialog);
    }

    private void createDialogListenerss(Dialog addImageDialog) {
        ImageView imageMachine = addImageDialog.findViewById(R.id.image_machine);
        ImageView imageTools = addImageDialog.findViewById(R.id.image_tools);
        ImageView imagePesticides = addImageDialog.findViewById(R.id.image_pesticides);
        ImageView imagePlant = addImageDialog.findViewById(R.id.image_plant);
        ImageView imageParagraph = addImageDialog.findViewById(R.id.image_paragraph);
        ImageView imageCalendar = addImageDialog.findViewById(R.id.image_calendar);
        ImageView imageMoney = addImageDialog.findViewById(R.id.image_money);
        ImageView imageClock = addImageDialog.findViewById(R.id.image_clock);
        ImageView imageFertilizer = addImageDialog.findViewById(R.id.image_fertilizer);
        ImageView imageNote = addImageDialog.findViewById(R.id.image_note);
        ImageView imageDropOfWater = addImageDialog.findViewById(R.id.image_drop_of_water);
        ImageView imagePepper = addImageDialog.findViewById(R.id.image_pepper);
        ImageView imageWeed = addImageDialog.findViewById(R.id.image_weed);
        ImageView imageHighrove = addImageDialog.findViewById(R.id.image_highgroves);
        ImageView imageFoil = addImageDialog.findViewById(R.id.image_foil);
        ImageView imagePegs = addImageDialog.findViewById(R.id.image_pegs);
        ImageView imageSeeds = addImageDialog.findViewById(R.id.image_seeds);
        ImageView imagePail = addImageDialog.findViewById(R.id.image_pail);
        ImageView imageFreeze = addImageDialog.findViewById(R.id.image_freeze);
        ImageView imageWorm = addImageDialog.findViewById(R.id.image_worm);
        ImageView imagePlace = addImageDialog.findViewById(R.id.image_place);
        ImageView imageThunderstorm = addImageDialog.findViewById(R.id.image_thunderstorm);
        ImageView imageMushroom = addImageDialog.findViewById(R.id.image_mushroom);
        ImageView imageWind = addImageDialog.findViewById(R.id.image_wind);
        ImageView imageTelephone = addImageDialog.findViewById(R.id.image_telephone);
        ImageView imageMoon = addImageDialog.findViewById(R.id.image_moon);
        ImageView imagePlus = addImageDialog.findViewById(R.id.image_plus);
        ImageView imageTemperathure = addImageDialog.findViewById(R.id.image_temperathure);
        ImageView imageLineChart = addImageDialog.findViewById(R.id.image_line_chart);
        ImageView imageDone = addImageDialog.findViewById(R.id.image_done);
        Button acceptButton = addImageDialog.findViewById(R.id.accept_button);
        Button cancelButton = addImageDialog.findViewById(R.id.cancel_button);

        View.OnClickListener listener = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                int id = v.getId();
                click=true;
                clearCheckedBackground();
                switch (id)
                {
                    case R.id.image_machine:{
                        imageMachine.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_machine;
                    }break;
                    case R.id.image_tools:{
                        imageTools.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_tools;
                    }break;
                    case R.id.image_pesticides:{
                        imagePesticides.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_bottle_of_pesticides;
                    }break;
                    case R.id.image_plant:{
                        imagePlant.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_plant;
                    }break;
                    case R.id.image_paragraph:{
                        imageParagraph.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_paragraph;
                    }break;
                    case R.id.image_calendar:{
                        imageCalendar.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_calendar;
                    }break;
                    case R.id.image_money:{
                        imageMoney.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_money;
                    }break;
                    case R.id.image_clock:{
                        imageClock.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_clock;
                    }break;
                    case R.id.image_fertilizer:{
                        imageFertilizer.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_fertilizer;
                    }break;
                    case R.id.image_note:{
                        imageNote.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_note;
                    }break;
                    case R.id.image_drop_of_water:{
                        imageDropOfWater.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_drop_of_water;
                    }break;
                    case R.id.image_pepper:{
                        imagePepper.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.grey_pepper;
                    }break;
                    case R.id.image_weed:{
                        imageWeed.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_weed;
                    }break;
                    case R.id.image_highgroves:{
                        imageHighrove.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_highgrove;
                    }break;
                    case R.id.image_foil:{
                        imageFoil.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_foil;
                    }break;
                    case R.id.image_pegs:{
                        imagePegs.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_pegs;
                    }break;
                    case R.id.image_seeds:{
                        imageSeeds.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_seeds;
                    }break;
                    case R.id.image_pail:{
                        imagePail.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_pail;
                    }break;
                    case R.id.image_freeze:{
                        imageFreeze.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_freeze;
                    }break;
                    case R.id.image_worm:{
                        imageWorm.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_worm;
                    }break;
                    case R.id.image_place:{
                        imagePlace.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_place;
                    }break;
                    case R.id.image_thunderstorm:{
                        imageThunderstorm.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_thunderstorm;
                    }break;
                    case R.id.image_mushroom:{
                        imageMushroom.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_mushrooms;
                    }break;
                    case R.id.image_wind:{
                        imageWind.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_wind;
                    }break;
                    case R.id.image_telephone:{
                        imageTelephone.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.image_telephone;
                    }break;
                    case R.id.image_moon:{
                        imageMoon.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_dark_mode;
                    }break;
                    case R.id.image_plus:{
                        imagePlus.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_plus;
                    }break;
                    case R.id.image_temperathure:{
                        imageTemperathure.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_dose;
                    }break;
                    case R.id.image_line_chart:{
                        imageLineChart.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_line_chart;
                    }break;
                    case R.id.image_done:{
                        imageDone.setBackgroundResource(R.drawable.shape_background_color_rectangle_with_main_color_stroke);
                        drawable=R.drawable.icon_done;
                    }break;
                }
            }

            private void clearCheckedBackground() {
                imageMachine.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageTools.setBackgroundResource(R.drawable.shape_black_rectangle);
                imagePesticides.setBackgroundResource(R.drawable.shape_black_rectangle);
                imagePlant.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageParagraph.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageCalendar.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageMoney.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageClock.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageFertilizer.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageNote.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageDropOfWater.setBackgroundResource(R.drawable.shape_black_rectangle);
                imagePepper.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageWeed.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageHighrove.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageFoil.setBackgroundResource(R.drawable.shape_black_rectangle);
                imagePegs.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageSeeds.setBackgroundResource(R.drawable.shape_black_rectangle);
                imagePail.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageFreeze.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageWorm.setBackgroundResource(R.drawable.shape_black_rectangle);
                imagePlace.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageThunderstorm.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageMushroom.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageWind.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageTelephone.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageMoon.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageTemperathure.setBackgroundResource(R.drawable.shape_black_rectangle);
                imagePlus.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageLineChart.setBackgroundResource(R.drawable.shape_black_rectangle);
                imageDone.setBackgroundResource(R.drawable.shape_black_rectangle);
            }
        };
        imageMachine.setOnClickListener(listener);
        imageTools.setOnClickListener(listener);
        imagePesticides.setOnClickListener(listener);
        imagePlant.setOnClickListener(listener);
        imageParagraph.setOnClickListener(listener);
        imageCalendar.setOnClickListener(listener);
        imageMoney.setOnClickListener(listener);
        imageClock.setOnClickListener(listener);
        imageFertilizer.setOnClickListener(listener);
        imageNote.setOnClickListener(listener);
        imageDropOfWater.setOnClickListener(listener);
        imagePepper.setOnClickListener(listener);
        imageWeed.setOnClickListener(listener);
        imageHighrove.setOnClickListener(listener);
        imageFoil.setOnClickListener(listener);
        imagePegs.setOnClickListener(listener);
        imageSeeds.setOnClickListener(listener);
        imagePail.setOnClickListener(listener);
        imageFreeze.setOnClickListener(listener);
        imageWorm.setOnClickListener(listener);
        imagePlace.setOnClickListener(listener);
        imageThunderstorm.setOnClickListener(listener);
        imageMushroom.setOnClickListener(listener);
        imageWind.setOnClickListener(listener);
        imageTelephone.setOnClickListener(listener);
        imageMoon.setOnClickListener(listener);
        imagePlus.setOnClickListener(listener);
        imageTemperathure.setOnClickListener(listener);
        imageLineChart.setOnClickListener(listener);
        imageDone.setOnClickListener(listener);

        @SuppressLint("NonConstantResourceId") View.OnClickListener listener1 = v -> {
            int id = v.getId();
            switch (id) {
                case R.id.accept_button: {
                    addImageDialog.dismiss();
                    image.setImageResource(drawable);
                    if(click)
                        addImageButton.setVisibility(View.INVISIBLE);
                }
                break;
                case R.id.cancel_button:
                {
                    drawable=0;
                    addImageDialog.dismiss();
                }break;
            }
        };
        acceptButton.setOnClickListener(listener1);
        cancelButton.setOnClickListener(listener1);
    }

}


