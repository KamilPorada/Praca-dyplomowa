package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import HelperClasses.ToolClass;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "epaprykarz.db";
    public static final int DATABASE_VERSION = 1;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE_TRADE_OF_PEPPER_ITEM = "CREATE TABLE " +
                DataBaseNames.TradeOfPepperItem.TABLE_NAME + " (" +
                DataBaseNames.TradeOfPepperItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER + " INTEGER NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_VAT + " INTEGER NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER + " INTEGER NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER + " REAL NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER + " REAL NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM + " REAL NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_DATE + " TEXT NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_PLACE + " TEXT NOT NULL" +
                ");";

        final String CREATE_TABLE_OUTGOINGS = "CREATE TABLE " +
                DataBaseNames.OutgoingsItem.TABLE_NAME + " (" +
                DataBaseNames.OutgoingsItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataBaseNames.OutgoingsItem.COLUMN_NAME + " TEXT NOT NULL," +
                DataBaseNames.OutgoingsItem.COLUMN_ID_OF_CATEGORY + " INTEGER NOT NULL," +
                DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING + " TEXT NOT NULL," +
                DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING + " REAL NOT NULL," +
                DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING + " TEXT NOT NULL" +
                ");";

        final String CREATE_TABLE_CATALOG_OF_PESTICIDES = "CREATE TABLE " +
                DataBaseNames.PesticidesItem.TABLE_NAME + " (" +
                DataBaseNames.PesticidesItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES + " TEXT NOT NULL," +
                DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PEST + " TEXT NOT NULL," +
                DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_PESTICIDE + " INTEGER NOT NULL," +
                DataBaseNames.PesticidesItem.COLUMN_DOSE + " REAL NOT NULL," +
                DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_DOSE + " INTEGER NOT NULL," +
                DataBaseNames.PesticidesItem.COLUMN_OF_GRACE + " INTEGER NOT NULL," +
                DataBaseNames.PesticidesItem.COLUMN_NOTES + " TEXT NOT NULL," +
                DataBaseNames.PesticidesItem.COLUMN_IMAGE + " INTEGER NOT NULL" +
                ");";

        final String CREATE_TABLE_OPERATIONS= "CREATE TABLE " +
                DataBaseNames.OperationsItem.TABLE_NAME + " (" +
                DataBaseNames.OperationsItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataBaseNames.OperationsItem.COLUMN_ID_PESTICIDE + " INTEGER NOT NULL," +
                DataBaseNames.OperationsItem.COLUMN_DATE + " TEXT NOT NULL," +
                DataBaseNames.OperationsItem.COLUMN_TIME + " TEXT NOT NULL," +
                DataBaseNames.OperationsItem.COLUMN_DATE_END_OF_GRACE + " TEXT NOT NULL," +
                DataBaseNames.OperationsItem.COLUMN_AGE_OF_PEPPER + " INTEGER NOT NULL," +
                DataBaseNames.OperationsItem.COLUMN_HIGHGROVES + " INTEGER NOT NULL," +
                DataBaseNames.OperationsItem.COLUMN_FLUID + " INTEGER NOT NULL," +
                DataBaseNames.OperationsItem.COLUMN_STATUS + " INTEGER NOT NULL" +
                ");";

        final String CREATE_TABLE_NOTES = "CREATE TABLE " +
                DataBaseNames.NotesItem.TABLE_NAME + " (" +
                DataBaseNames.NotesItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataBaseNames.NotesItem.COLUMN_TITLE + " TEXT NOT NULL," +
                DataBaseNames.NotesItem.COLUMN_DATE + " TEXT NOT NULL," +
                DataBaseNames.NotesItem.COLUMN_DESCRIBE + " TEXT NOT NULL," +
                DataBaseNames.NotesItem.COLUMN_IMAGE + " INTEGER NOT NULL" +
                ");";

        final String CREATE_TABLE_WATER_PLANTATION = "CREATE TABLE " +
                DataBaseNames.WaterPlantationItem.TABLE_NAME + " (" +
                DataBaseNames.WaterPlantationItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataBaseNames.WaterPlantationItem.COLUMN_EFFICIENCY_OF_PUMP + " REAL NOT NULL," +
                DataBaseNames.WaterPlantationItem.COLUMN_DATE + " TEXT NOT NULL," +
                DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_HIGHGROVES_IN_EACH_ROUND + " TEXT NOT NULL," +
                DataBaseNames.WaterPlantationItem.COLUMN_TIMES_OF_EACH_ROUND + " TEXT NOT NULL," +
                DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_ROUND + " INTEGER NOT NULL," +
                DataBaseNames.WaterPlantationItem.COLUMN_STATUS + " INTEGER NOT NULL" +
                ");";

        final String CREATE_TABLE_LOCATIONS = "CREATE TABLE " +
                DataBaseNames.LocationItem.TABLE_NAME + " (" +
                DataBaseNames.LocationItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataBaseNames.LocationItem.COLUMN_NAME_OF_LOCATION + " TEXT NOT NULL," +
                DataBaseNames.LocationItem.COLUMN_LATITUDE + " REAL NOT NULL," +
                DataBaseNames.LocationItem.COLUMN_LONGITUDE + " REAL NOT NULL" +
                ");";

        final String CREATE_TABLE_MARKETS = "CREATE TABLE " +
                DataBaseNames.MarketItem.TABLE_NAME + " (" +
                DataBaseNames.MarketItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataBaseNames.MarketItem.COLUMN_NAME + " TEXT NOT NULL," +
                DataBaseNames.MarketItem.COLUMN_LATITUDE + " REAL NOT NULL," +
                DataBaseNames.MarketItem.COLUMN_LONGITUDE + " REAL NOT NULL," +
                DataBaseNames.MarketItem.COLUMN_ADDRESS + " TEXT NOT NULL," +
                DataBaseNames.MarketItem.COLUMN_EMAIL + " TEXT NOT NULL," +
                DataBaseNames.MarketItem.COLUMN_NUMBER + " TEXT NOT NULL" +
                ");";

        db.execSQL(CREATE_TABLE_TRADE_OF_PEPPER_ITEM);
        db.execSQL(CREATE_TABLE_OUTGOINGS);
        db.execSQL(CREATE_TABLE_CATALOG_OF_PESTICIDES);
        db.execSQL(CREATE_TABLE_OPERATIONS);
        db.execSQL(CREATE_TABLE_NOTES);
        db.execSQL(CREATE_TABLE_WATER_PLANTATION);
        db.execSQL(CREATE_TABLE_LOCATIONS);
        db.execSQL(CREATE_TABLE_MARKETS);

        ToolClass.fillCatalogOfPesticides(db);
        ToolClass.fillMarkets(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.TradeOfPepperItem.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.OutgoingsItem.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.PesticidesItem.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.PesticidesItem.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.NotesItem.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.WaterPlantationItem.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.LocationItem.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.MarketItem.TABLE_NAME);

        onCreate(db);
    }

    //----------------------------TRADE_OF_PEPPER SQLITE QUERIES-------------------------------//

    public void addTradeOfPepperItem(int color, int vat, int clas, double price, double weight, double sum,
                                     String date, String place) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER, color);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_VAT, vat);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER, clas);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER, price);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER, weight);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM, sum);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_DATE, date);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_PLACE, place);

        db.insertOrThrow(DataBaseNames.TradeOfPepperItem.TABLE_NAME,null,values);
    }
    public void updateTradeOfTradeOfPepperItems(int id, int color, int vat,
                                                int clas, double price, double weight, double sum,
                                                String date, String place) {
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER,color);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_VAT,vat);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER,clas);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER,price);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER,weight);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM,sum);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_DATE,date);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_PLACE,place);

        db.update(DataBaseNames.TradeOfPepperItem.TABLE_NAME,values,DataBaseNames.TradeOfPepperItem._ID + " LIKE " + id,null);
    }
    public Cursor getTradeOfPepperItems(){
        String[] columns={DataBaseNames.TradeOfPepperItem._ID, DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER,
                          DataBaseNames.TradeOfPepperItem.COLUMN_VAT, DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER,
                          DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER, DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER,
                          DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM,DataBaseNames.TradeOfPepperItem.COLUMN_DATE,
                          DataBaseNames.TradeOfPepperItem.COLUMN_PLACE};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.TradeOfPepperItem.TABLE_NAME,columns, null,null,null,null,null);
    }

    public Cursor getSpecifyTradeOfPepperValues(int id) {
        String[] columns={DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER,
                DataBaseNames.TradeOfPepperItem.COLUMN_VAT, DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER,
                DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER, DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER,
                DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM,DataBaseNames.TradeOfPepperItem.COLUMN_DATE,
                DataBaseNames.TradeOfPepperItem.COLUMN_PLACE};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.TradeOfPepperItem.TABLE_NAME, columns, DataBaseNames.TradeOfPepperItem._ID + " LIKE " + id,null,null,null,null);
    }

    //-----------------------------OUTGOINGS SQLITE QUERIES---------------------------------//

    public void addOutgoing(String category, int idOfCategory, String describe, double price, String date) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseNames.OutgoingsItem.COLUMN_NAME,category);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_ID_OF_CATEGORY,idOfCategory);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING,describe);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING,price);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING,date);

        db.insertOrThrow(DataBaseNames.OutgoingsItem.TABLE_NAME,null,values);
    }
    public void updateOutgoingItems(int id, String category, int idOfCategory, String describe, double price, String date) {
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(DataBaseNames.OutgoingsItem.COLUMN_NAME,category);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_ID_OF_CATEGORY,idOfCategory);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING,describe);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING,price);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING,date);

        db.update(DataBaseNames.OutgoingsItem.TABLE_NAME,values,DataBaseNames.OutgoingsItem._ID + " LIKE " + id,null);
    }
    public Cursor getOutgoingItems(){
        String[] columns={DataBaseNames.OutgoingsItem._ID, DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING,
                DataBaseNames.OutgoingsItem.COLUMN_ID_OF_CATEGORY, DataBaseNames.OutgoingsItem.COLUMN_NAME,
                DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING, DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING,};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.OutgoingsItem.TABLE_NAME,columns, null,null,null,null,null);
    }

    public Cursor getSpecifyOutgoingValues(int id) {
        String[] columns={DataBaseNames.OutgoingsItem.COLUMN_NAME, DataBaseNames.OutgoingsItem.COLUMN_ID_OF_CATEGORY,  DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING,
                          DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING, DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.OutgoingsItem.TABLE_NAME, columns, DataBaseNames.OutgoingsItem._ID + " LIKE " + id,null,null,null,null);
    }

    //------------------------------STATISTICS SQLITE QUERIES---------------------------------//

    public Cursor getWeightFromColor(int color){
        String[] columns={DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER,
                          DataBaseNames.TradeOfPepperItem.COLUMN_DATE};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.TradeOfPepperItem.TABLE_NAME, columns, DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER + " LIKE " + color, null,null,null,null);
    }
    public Cursor getWeightFromClass(int clas){
        String[] columns={DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER,
                DataBaseNames.TradeOfPepperItem.COLUMN_DATE};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.TradeOfPepperItem.TABLE_NAME, columns, DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER + " LIKE " + clas, null,null,null,null);
    }
    public Cursor getMoneyFromTrade(){
        String[] columns={DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM, DataBaseNames.TradeOfPepperItem.COLUMN_DATE};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.TradeOfPepperItem.TABLE_NAME,columns, null,null,null,null,null);
    }
    public Cursor getWeightFromTrade(){
        String[] columns={DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER, DataBaseNames.TradeOfPepperItem.COLUMN_DATE};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.TradeOfPepperItem.TABLE_NAME,columns, null,null,null,null,null);
    }
    public Cursor getPriceWeightAndDateFromTrade(int color, int clas){
        String[] columns={DataBaseNames.TradeOfPepperItem.COLUMN_DATE, DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM,
                          DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.TradeOfPepperItem.TABLE_NAME, columns, DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER + " LIKE " + color + " AND " + DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER + " LIKE " + clas,null,null,null,null);
    }
    public Cursor getMoneyFromSpecificOutgoing(String category){
        String[] columns={DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING, DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING};
        String selection = DataBaseNames.OutgoingsItem.COLUMN_NAME + " LIKE ?";
        String[] selection_args= {category};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.OutgoingsItem.TABLE_NAME, columns, selection,selection_args,null,null,null);
    }
    public Cursor getMoneyFromOutgoings(){
        String[] columns={DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING, DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.OutgoingsItem.TABLE_NAME,columns, null,null,null,null,null);
    }

    //------------------------------UNIVERSAL SQLITE QUERIES---------------------------------//

    public void deleteItem(String tableName, int id)
    {
        SQLiteDatabase db=getReadableDatabase();
        String[] args ={id+""};
        db.delete(tableName,"_id=?",args);
    }





    public Cursor getPesticideCatalogData(){
        String[] columns={DataBaseNames.PesticidesItem._ID, DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES,
                          DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PEST, DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_PESTICIDE,
                          DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_DOSE, DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_DOSE,
                          DataBaseNames.PesticidesItem.COLUMN_OF_GRACE, DataBaseNames.PesticidesItem.COLUMN_NOTES,
                          DataBaseNames.PesticidesItem.COLUMN_IMAGE};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.PesticidesItem.TABLE_NAME,columns, null,null,null,null,null);
    }

    public Cursor getSpecifyPesticideValues(String name) {
        String[] columns={DataBaseNames.PesticidesItem._ID, DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PEST,
                          DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_PESTICIDE, DataBaseNames.PesticidesItem.COLUMN_DOSE,
                          DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_DOSE, DataBaseNames.PesticidesItem.COLUMN_OF_GRACE,
                          DataBaseNames.PesticidesItem.COLUMN_NOTES, DataBaseNames.PesticidesItem.COLUMN_IMAGE};
        String selection = DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES+" LIKE ?";
        String[] selection_args= {name};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.PesticidesItem.TABLE_NAME, columns, selection,selection_args,null,null,null);
    }

    public Cursor getSpecifyPesticideValues(int id) {
        String[] columns={DataBaseNames.PesticidesItem._ID, DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES, DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PEST,
                DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_PESTICIDE, DataBaseNames.PesticidesItem.COLUMN_DOSE,
                DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_DOSE, DataBaseNames.PesticidesItem.COLUMN_OF_GRACE,
                DataBaseNames.PesticidesItem.COLUMN_NOTES, DataBaseNames.PesticidesItem.COLUMN_IMAGE};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.PesticidesItem.TABLE_NAME, columns, DataBaseNames.PesticidesItem._ID + " LIKE " + id,null,null,null,null);
    }




    public void addOperation(int idPesticide, String date, String time, String dateOfEndGrace, int ageOfPepper, int highgroves, int fluid, int status) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseNames.OperationsItem.COLUMN_ID_PESTICIDE,idPesticide);
        values.put(DataBaseNames.OperationsItem.COLUMN_DATE,date);
        values.put(DataBaseNames.OperationsItem.COLUMN_TIME,time);
        values.put(DataBaseNames.OperationsItem.COLUMN_DATE_END_OF_GRACE,dateOfEndGrace);
        values.put(DataBaseNames.OperationsItem.COLUMN_AGE_OF_PEPPER,ageOfPepper);
        values.put(DataBaseNames.OperationsItem.COLUMN_HIGHGROVES,highgroves);
        values.put(DataBaseNames.OperationsItem.COLUMN_FLUID,fluid);
        values.put(DataBaseNames.OperationsItem.COLUMN_STATUS, status);

        db.insertOrThrow(DataBaseNames.OperationsItem.TABLE_NAME,null,values);
    }

    public Cursor getSpecifyOperationsValues(int id) {
        String[] columns={DataBaseNames.OperationsItem.COLUMN_ID_PESTICIDE, DataBaseNames.OperationsItem.COLUMN_DATE, DataBaseNames.OperationsItem.COLUMN_TIME,
                          DataBaseNames.OperationsItem.COLUMN_DATE_END_OF_GRACE, DataBaseNames.OperationsItem.COLUMN_AGE_OF_PEPPER,
                          DataBaseNames.OperationsItem.COLUMN_HIGHGROVES, DataBaseNames.OperationsItem.COLUMN_FLUID,
                          DataBaseNames.OperationsItem.COLUMN_STATUS};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.OperationsItem.TABLE_NAME, columns, DataBaseNames.OperationsItem._ID + " LIKE " + id,null,null,null,null);
    }

    public Cursor getOperationCatalogData(){
        String[] columns={DataBaseNames.OperationsItem._ID, DataBaseNames.OperationsItem.COLUMN_DATE,
                DataBaseNames.OperationsItem.COLUMN_ID_PESTICIDE, DataBaseNames.OperationsItem.COLUMN_TIME,
                DataBaseNames.OperationsItem.COLUMN_DATE_END_OF_GRACE, DataBaseNames.OperationsItem.COLUMN_AGE_OF_PEPPER,
                DataBaseNames.OperationsItem.COLUMN_HIGHGROVES, DataBaseNames.OperationsItem.COLUMN_FLUID,
                DataBaseNames.OperationsItem.COLUMN_STATUS};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.OperationsItem.TABLE_NAME,columns, null,null,null,null,null);
    }

    public void updateOperationStatus(int id, int status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseNames.OperationsItem.COLUMN_STATUS,status);

        db.update(DataBaseNames.OperationsItem.TABLE_NAME,values,DataBaseNames.OperationsItem._ID + " LIKE " + id,null);
    }







    public void addNote(String title, String date, String describe, int image) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseNames.NotesItem.COLUMN_TITLE, title);
        values.put(DataBaseNames.NotesItem.COLUMN_DATE, date);
        values.put(DataBaseNames.NotesItem.COLUMN_DESCRIBE, describe);
        values.put(DataBaseNames.NotesItem.COLUMN_IMAGE, image);
        db.insertOrThrow(DataBaseNames.NotesItem.TABLE_NAME,null,values);
    }

    public Cursor getNotes(){
        String[] columns={DataBaseNames.NotesItem._ID, DataBaseNames.NotesItem.COLUMN_TITLE,
                          DataBaseNames.NotesItem.COLUMN_DATE, DataBaseNames.NotesItem.COLUMN_DESCRIBE,
                          DataBaseNames.NotesItem.COLUMN_IMAGE};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.NotesItem.TABLE_NAME,columns, null,null,null,null,null);
    }

    public void updateNote(int id, String title, String date, String describe, int image) {
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(DataBaseNames.NotesItem.COLUMN_TITLE,title);
        values.put(DataBaseNames.NotesItem.COLUMN_DATE,date);
        values.put(DataBaseNames.NotesItem.COLUMN_DESCRIBE,describe);
        values.put(DataBaseNames.NotesItem.COLUMN_IMAGE,image);

        db.update(DataBaseNames.NotesItem.TABLE_NAME,values,DataBaseNames.NotesItem._ID + " LIKE " + id,null);
    }

    public Cursor getSpecifyNotesValues(int id) {
        String[] columns={DataBaseNames.NotesItem.COLUMN_TITLE, DataBaseNames.NotesItem.COLUMN_DATE,
                          DataBaseNames.NotesItem.COLUMN_DESCRIBE, DataBaseNames.NotesItem.COLUMN_IMAGE};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.NotesItem.TABLE_NAME, columns, DataBaseNames.NotesItem._ID + " LIKE " + id,null,null,null,null);
    }






    public void addWaterPlantation(double efficiency, String date, String highgroves, String times, int rounds, int status) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseNames.WaterPlantationItem.COLUMN_EFFICIENCY_OF_PUMP,efficiency);
        values.put(DataBaseNames.WaterPlantationItem.COLUMN_DATE,date);
        values.put(DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_HIGHGROVES_IN_EACH_ROUND,highgroves);
        values.put(DataBaseNames.WaterPlantationItem.COLUMN_TIMES_OF_EACH_ROUND,times);
        values.put(DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_ROUND,rounds);
        values.put(DataBaseNames.WaterPlantationItem.COLUMN_STATUS,status);
        db.insertOrThrow(DataBaseNames.WaterPlantationItem.TABLE_NAME,null,values);
    }

    public Cursor getWateringPlantationItems(){
        String[] columns={DataBaseNames.WaterPlantationItem._ID, DataBaseNames.WaterPlantationItem.COLUMN_EFFICIENCY_OF_PUMP,
                          DataBaseNames.WaterPlantationItem.COLUMN_DATE, DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_HIGHGROVES_IN_EACH_ROUND,
                          DataBaseNames.WaterPlantationItem.COLUMN_TIMES_OF_EACH_ROUND, DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_ROUND,
                          DataBaseNames.WaterPlantationItem.COLUMN_STATUS};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.WaterPlantationItem.TABLE_NAME,columns, null,null,null,null,null);
    }

    public Cursor getSpecifyWateringPlantationValues(int id) {
        String[] columns={DataBaseNames.WaterPlantationItem.COLUMN_EFFICIENCY_OF_PUMP,
                DataBaseNames.WaterPlantationItem.COLUMN_DATE, DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_HIGHGROVES_IN_EACH_ROUND,
                DataBaseNames.WaterPlantationItem.COLUMN_TIMES_OF_EACH_ROUND, DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_ROUND,
                DataBaseNames.WaterPlantationItem.COLUMN_STATUS};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.WaterPlantationItem.TABLE_NAME, columns, DataBaseNames.NotesItem._ID + " LIKE " + id,null,null,null,null);
    }

    public void updateSpecifyWatering(int id, int status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseNames.WaterPlantationItem.COLUMN_STATUS,status);

        db.update(DataBaseNames.WaterPlantationItem.TABLE_NAME,values,DataBaseNames.WaterPlantationItem._ID + " LIKE " + id,null);
    }

    public void addLocation(String name, double latitude, double longitude) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseNames.LocationItem.COLUMN_NAME_OF_LOCATION,name);
        values.put(DataBaseNames.LocationItem.COLUMN_LATITUDE,latitude);
        values.put(DataBaseNames.LocationItem.COLUMN_LONGITUDE,longitude);

        db.insertOrThrow(DataBaseNames.LocationItem.TABLE_NAME,null,values);
    }

    public Cursor getLocations(){
        String[] columns={DataBaseNames.LocationItem._ID, DataBaseNames.LocationItem.COLUMN_NAME_OF_LOCATION,
                          DataBaseNames.LocationItem.COLUMN_LATITUDE, DataBaseNames.LocationItem.COLUMN_LONGITUDE};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.LocationItem.TABLE_NAME,columns, null,null,null,null,null);
    }

    public Cursor getSpecifyLocation(int id) {
        String[] columns={DataBaseNames.LocationItem.COLUMN_NAME_OF_LOCATION,
                          DataBaseNames.LocationItem.COLUMN_LATITUDE, DataBaseNames.LocationItem.COLUMN_LONGITUDE};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.LocationItem.TABLE_NAME, columns, DataBaseNames.LocationItem._ID + " LIKE " + id,null,null,null,null);
    }

    public Cursor getMarkets(){
        String[] columns={DataBaseNames.MarketItem._ID, DataBaseNames.MarketItem.COLUMN_NAME, DataBaseNames.MarketItem.COLUMN_LATITUDE,
                          DataBaseNames.MarketItem.COLUMN_LONGITUDE, DataBaseNames.MarketItem.COLUMN_ADDRESS,
                          DataBaseNames.MarketItem.COLUMN_EMAIL, DataBaseNames.MarketItem.COLUMN_NUMBER};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.MarketItem.TABLE_NAME,columns, null,null,null,null,null);
    }

    public Cursor getSpecifyMarkets(String title) {
        String[] columns={DataBaseNames.MarketItem.COLUMN_ADDRESS,
                          DataBaseNames.MarketItem.COLUMN_EMAIL, DataBaseNames.MarketItem.COLUMN_NUMBER};
        String selection = DataBaseNames.MarketItem.COLUMN_NAME + " LIKE ?";
        String[] selection_args= {title};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.MarketItem.TABLE_NAME, columns, selection,selection_args,null,null,null);
    }

}

