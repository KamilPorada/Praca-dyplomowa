package MainClass;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import DataBase.SharedPreferencesNames;
import FragmentsClass.BottomFragments.AboutApplicationFragment;
import FragmentsClass.BottomFragments.FarmFragment;
import FragmentsClass.BottomFragments.HomeFragment;
import FragmentsClass.MainModulesFragments.Calculators.CalculatorsFragment;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterFragment;
import FragmentsClass.MainModulesFragments.IncomeDaily.IncomeDailyFragment;
import FragmentsClass.MainModulesFragments.Notes.NotesFragment;
import FragmentsClass.MainModulesFragments.Operations.OperationsFragment;
import FragmentsClass.MainModulesFragments.SavedLocations.SavedLocationsFragment;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private NavigationView drawerView;
    private Toolbar toolbar;
    private TextView toolSeason, howOwner;
    private DrawerLayout drawerLayout;
    private Fragment fragment = null;
    private TextView age;

    int position=10, chooseYear, chooseCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        startSettings();
        createListeners();
        setOwner();
    }

    @SuppressLint("SetTextI18n")
    private void setOwner() {
        View navHeader = drawerView.getHeaderView(0);
        howOwner = navHeader.findViewById(R.id.how_owner);
        SharedPreferences sharedPreferences = this.getSharedPreferences(SharedPreferencesNames.FarmData.NAME,Context.MODE_PRIVATE);
        String owner = sharedPreferences.getString(SharedPreferencesNames.FarmData.OWNER, "-");
        howOwner.setText("Właściciel\n" + owner);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tollbar_menu_default, menu);
        return true;
    }


    private void findViews() {
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        toolSeason=findViewById(R.id.tool_season);
        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        drawerView=findViewById(R.id.drawer_view);

    }

    @SuppressLint("SetTextI18n")
    private void startSettings() {
        fragment=new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        setSupportActionBar(toolbar);
        drawerView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        toolSeason.setText(toolSeason.getText()+" "+ ToolClass.getActualYear());
        ToolClass.clearTemporaryCurrentOperations(this);
    }

    @SuppressLint("NonConstantResourceId")
    private void createListeners() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id=item.getItemId();
            switch (id)
            {
                case R.id.bottom_farm:
                {
                    fragment=new FarmFragment();
                }break;
                case R.id.bottom_home:
                {
                    fragment=new HomeFragment();
                }break;
                case R.id.bottom_application:
                {
                    fragment=new AboutApplicationFragment();
                }break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.navHome:
            {
                fragment = new HomeFragment();
            }break;
            case R.id.navMyFarm:
            {
                fragment = new FarmFragment();
            }break;
            case R.id.navAboutApplication:
            {
                fragment = new AboutApplicationFragment();
            }break;
            case R.id.navCalculator:
            {
                fragment = new CalculatorsFragment();
            }break;
            case R.id.navNotes:
            {
                fragment = new NotesFragment();
            }break;
            case R.id.navOperations:
            {
                fragment = new OperationsFragment();
            }break;
            case R.id.navControlOfWater:
            {
                fragment = new ControlOfWaterFragment();
            }break;
            case R.id.navIncomeNotes:
            {
                fragment = new IncomeDailyFragment();
            }break;
            case R.id.navImportantPlaces:
            {
                fragment = new SavedLocationsFragment();
            }break;
            case R.id.navArchive:
            {
                openChoseDataDialog();
            }break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openChoseDataDialog() {
        Dialog choseDataDialog = new Dialog(this);
        choseDataDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        choseDataDialog.setContentView(R.layout.dialog_export_data);
        choseDataDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        choseDataDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        choseDataDialog.show();
        createDialogListeners(choseDataDialog);
    }

    private void createDialogListeners(Dialog choseDataDialog) {
        ImageView left = choseDataDialog.findViewById(R.id.left);
        ImageView right = choseDataDialog.findViewById(R.id.right);
        TextSwitcher howAge = choseDataDialog.findViewById(R.id.how_age);
        ConstraintLayout firstBlock = choseDataDialog.findViewById(R.id.first_block);
        ConstraintLayout secondBlock = choseDataDialog.findViewById(R.id.second_block);
        ConstraintLayout thirdBlock = choseDataDialog.findViewById(R.id.third_block);
        ConstraintLayout fourthBlock = choseDataDialog.findViewById(R.id.fourth_block);
        ConstraintLayout fivethBlock = choseDataDialog.findViewById(R.id.fiveth_block);
        Button btnCancel = choseDataDialog.findViewById(R.id.btn_cancel);

        int[] years = new int[20];

        for (int i=-10;i<10;i++)
            years[i+10]=ToolClass.getActualYear()+i;

        chooseYear=years[position];

        left.setOnClickListener(v -> {
            if(position==0){
                chooseYear=years[position];
                left.setEnabled(false);
            }
            else {
                position--;
                chooseYear=years[position];
                right.setEnabled(true);
                howAge.setText("Sezon " + chooseYear);

            }
        });

        right.setOnClickListener(v -> {
            if(position==years.length-1){
                position=years.length-1;
                chooseYear=years[position];
                right.setEnabled(false);
            }
            else{
                position++;
                chooseYear=years[position];
                left.setEnabled(true);
                howAge.setText("Sezon " + chooseYear);
            }
        });

        howAge.setFactory(() -> {
            age = new TextView(MainActivity.this);
            age.setTextColor(ContextCompat.getColor(this, R.color.blackToWhite));
            age.setTextSize(30);
            age.setGravity(Gravity.CENTER_HORIZONTAL);
            return age;
        });

        howAge.setText("Sezon " + chooseYear);

        btnCancel.setOnClickListener(v -> choseDataDialog.dismiss());

        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.first_block:
                {
                    chooseCategory=1;
                }break;
                case R.id.second_block:
                {
                    chooseCategory=2;
                }break;
                case R.id.third_block:
                {
                    chooseCategory=3;
                }break;
                case R.id.fourth_block:
                {
                    chooseCategory=4;
                }break;
                case R.id.fiveth_block:
                {
                    chooseCategory=5;
                }break;
            }
            exportDataToXLS(choseDataDialog);
        };
        firstBlock.setOnClickListener(listener);
        secondBlock.setOnClickListener(listener);
        thirdBlock.setOnClickListener(listener);
        fourthBlock.setOnClickListener(listener);
        fivethBlock.setOnClickListener(listener);
    }

    @SuppressLint("DefaultLocale")
    private void exportDataToXLS(Dialog choseDataDialog) {
        ShowToast toast = new ShowToast();
        DataBaseHelper db = new DataBaseHelper(this);
        Cursor cursor;
        int r =1, c =0;
        File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "default");
        HSSFWorkbook workbook = new HSSFWorkbook();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        switch (chooseCategory)
        {
            case 1:
            {
                filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Notatki gospodarstwa " + chooseYear + ".xls");
                HSSFSheet sheet = workbook.createSheet("Notatki gospodarstwa " + chooseYear);

                HSSFRow row = sheet.createRow(0);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue("Data");
                cell = row.createCell(1);
                cell.setCellValue("Tytuł notatki");
                cell = row.createCell(2);
                cell.setCellValue("Treść notatki");

                sheet.setColumnWidth(0,2500);
                sheet.setColumnWidth(1,4500);
                sheet.setColumnWidth(2,30000);

                cursor = db.getNotes();
                while (cursor.moveToNext())
                {
                    String date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.NotesItem.COLUMN_DATE));
                    if(ToolClass.getYear(date)==chooseYear){
                        row = sheet.createRow(r);
                        cell = row.createCell(c);
                        cell.setCellValue(date);
                        c++;

                        String title=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.NotesItem.COLUMN_TITLE));
                        cell = row.createCell(c);
                        cell.setCellValue(title);
                        c++;

                        String describe=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.NotesItem.COLUMN_DESCRIBE));
                        cell = row.createCell(c);
                        cell.setCellValue(describe);
                        c =0;
                        r++;
                    }
                }
            }break;
            case 2:
            {
                filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Zabiegi pielęgnacyjne " + chooseYear + ".xls");
                HSSFSheet sheet = workbook.createSheet("Zabiegi pielęgnacyjne " + chooseYear);

                HSSFRow row = sheet.createRow(0);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue("Data");
                cell = row.createCell(1);
                cell.setCellValue("Godzina");
                cell = row.createCell(2);
                cell.setCellValue("Pestycyd");
                cell = row.createCell(3);
                cell.setCellValue("Data zakończenia karencji");
                cell = row.createCell(4);
                cell.setCellValue("Wiek papryki");
                cell = row.createCell(5);
                cell.setCellValue("Ilość tuneli");
                cell = row.createCell(6);
                cell.setCellValue("Ilość cieczy roboczej");
                cell = row.createCell(7);
                cell.setCellValue("Status");

                sheet.setColumnWidth(0,2500);
                sheet.setColumnWidth(1,1850);
                sheet.setColumnWidth(2,5000);
                sheet.setColumnWidth(3,6000);
                sheet.setColumnWidth(4,3000);
                sheet.setColumnWidth(5,2500);
                sheet.setColumnWidth(6,4500);
                sheet.setColumnWidth(7,2750);



                cursor = db.getOperationCatalogData();
                while (cursor.moveToNext())
                {
                    String date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_DATE));
                    if(ToolClass.getYear(date)==chooseYear){
                        row = sheet.createRow(r);
                        cell = row.createCell(c);
                        cell.setCellValue(date);
                        c++;

                        String time=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_TIME));
                        cell = row.createCell(c);
                        cell.setCellValue(time);
                        c++;

                        int idOfPesticide = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_ID_PESTICIDE));
                        Cursor cr = db.getSpecifyPesticideValues(idOfPesticide);
                        cr.moveToFirst();
                        String pesticide = cr.getString(cr.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES));
                        cell = row.createCell(c);
                        cell.setCellValue(pesticide);
                        c++;

                        String endOfGrace=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_DATE_END_OF_GRACE));
                        cell = row.createCell(c);
                        cell.setCellValue(endOfGrace);
                        c++;

                        int age=cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_AGE_OF_PEPPER));
                        cell = row.createCell(c);
                        cell.setCellValue(age + " dni");
                        c++;

                        int highgroves=cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_HIGHGROVES));
                        cell = row.createCell(c);
                        cell.setCellValue(highgroves);
                        c++;

                        int fluid=cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_FLUID));
                        cell = row.createCell(c);
                        cell.setCellValue(fluid + "l");
                        c++;

                        int status=cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_STATUS));
                        cell = row.createCell(c);
                        if(status==0)
                            cell.setCellValue("Zaplanowano");
                        else
                            cell.setCellValue("Wykonano");
                        c=0;
                        r++;
                    }
                }
            }break;
            case 3:
            {
                filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Dziennik dochodów " + chooseYear + ".xls");
                HSSFSheet sheet = workbook.createSheet("Dziennik dochodów " + chooseYear);

                HSSFRow row = sheet.createRow(0);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue("Data");
                cell = row.createCell(1);
                cell.setCellValue("Kolor");
                cell = row.createCell(2);
                cell.setCellValue("Klasa");
                cell = row.createCell(3);
                cell.setCellValue("Cena");
                cell = row.createCell(4);
                cell.setCellValue("Waga");
                cell = row.createCell(5);
                cell.setCellValue("VAT");
                cell = row.createCell(6);
                cell.setCellValue("Suma");
                cell = row.createCell(7);
                cell.setCellValue("Miejsce sprzedaży");

                sheet.setColumnWidth(0,2500);
                sheet.setColumnWidth(1,3000);
                sheet.setColumnWidth(2,1750);
                sheet.setColumnWidth(3,2000);
                sheet.setColumnWidth(4,2500);
                sheet.setColumnWidth(5,1000);
                sheet.setColumnWidth(6,3000);
                sheet.setColumnWidth(7,10000);

                cursor = db.getTradeOfPepperItems();
                while (cursor.moveToNext())
                {
                    String date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
                    if(ToolClass.getYear(date)==chooseYear){
                        row = sheet.createRow(r);
                        cell = row.createCell(c);
                        cell.setCellValue(date);
                        c++;

                        int color=cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER));
                        cell = row.createCell(c);
                        if(color==0)
                            cell.setCellValue("czerwona");
                        else if(color==1)
                            cell.setCellValue("żółta");
                        else if(color==2)
                            cell.setCellValue("zielona");
                        else if(color==3)
                            cell.setCellValue("pomarańczowa");
                        else
                            cell.setCellValue("blondyna");
                        c++;

                        String clas=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER));
                        cell = row.createCell(c);
                        cell.setCellValue(clas);
                        c++;

                        double price=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER));
                        cell = row.createCell(c);
                        cell.setCellValue(price + " zł");
                        c++;

                        double weight=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
                        cell = row.createCell(c);
                        cell.setCellValue(weight + " kg");
                        c++;

                        int vat=cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_VAT));
                        cell = row.createCell(c);
                        cell.setCellValue(vat+"%");
                        c++;

                        double sum=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM));
                        cell = row.createCell(c);
                        cell.setCellValue(String.format("%.2f", Math.round((sum) * 100.0) / 100.0) + " zł");
                        c++;

                        String place=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_PLACE));
                        cell = row.createCell(c);
                        cell.setCellValue(place);
                        c=0;
                        r++;
                    }
                }
            }break;
            case 4:
            {
                filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Dziennik wydatków " + chooseYear + ".xls");
                HSSFSheet sheet = workbook.createSheet("Dziennik wydatków " + chooseYear);

                HSSFRow row = sheet.createRow(0);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue("Data");
                cell = row.createCell(1);
                cell.setCellValue("Kategoria");
                cell = row.createCell(2);
                cell.setCellValue("Cena");
                cell = row.createCell(3);
                cell.setCellValue("Opis");

                sheet.setColumnWidth(0,2500);
                sheet.setColumnWidth(1,5000);
                sheet.setColumnWidth(2,2300);
                sheet.setColumnWidth(3,25000);


                cursor = db.getOutgoingItems();
                while (cursor.moveToNext())
                {
                    String date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING));
                    if(ToolClass.getYear(date)==chooseYear){
                        row = sheet.createRow(r);
                        cell = row.createCell(c);
                        cell.setCellValue(date);
                        c++;

                        String category=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_NAME));
                        cell = row.createCell(c);
                        cell.setCellValue(category);
                        c++;

                        double price=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING));
                        cell = row.createCell(c);
                        cell.setCellValue(price + " zł");
                        c++;

                        String describe=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING));
                        cell = row.createCell(c);
                        cell.setCellValue(describe);
                        c=0;
                        r++;
                    }
                }
            }break;
            case 5:
            {
                filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Zapisane lokalizacje.xls");
                HSSFSheet sheet = workbook.createSheet("Zapisane lokalizacje");

                HSSFRow row = sheet.createRow(0);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue("Nazwa");
                cell = row.createCell(1);
                cell.setCellValue("Długość geograficzna");
                cell = row.createCell(2);
                cell.setCellValue("Szerokość geograficzna");

                sheet.setColumnWidth(0,6500);
                sheet.setColumnWidth(1,5500);
                sheet.setColumnWidth(2,5500);

                cursor = db.getLocations();
                while (cursor.moveToNext())
                {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.LocationItem.COLUMN_NAME_OF_LOCATION));
                    row = sheet.createRow(r);
                    cell = row.createCell(c);
                    cell.setCellValue(name);
                    c++;

                    double longitude=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.LocationItem.COLUMN_LONGITUDE));
                    cell = row.createCell(c);
                    cell.setCellValue(ToolClass.generateStringCoordinate(longitude) + " N");
                    c++;

                    double latitude=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.LocationItem.COLUMN_LATITUDE));
                    cell = row.createCell(c);
                    cell.setCellValue(ToolClass.generateStringCoordinate(latitude) + " E");
                    c=0;
                    r++;
                }
            }break;
        }
        try {
            if (!filePath.exists()){
                filePath.createNewFile();
            }
            FileOutputStream fileOutputStream= new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            toast.showErrorToast(this,"UWAGA!\n  Błąd eksportu danych!",R.drawable.icon_information);
        }

        toast.showSuccessfulToast(this,"SUKCES!\n  Dane zostały wyeksportowane!");
        choseDataDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}