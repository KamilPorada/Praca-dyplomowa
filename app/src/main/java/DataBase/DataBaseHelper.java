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

        db.execSQL(CREATE_TABLE_TRADE_OF_PEPPER_ITEM);
        db.execSQL(CREATE_TABLE_OUTGOINGS);
        db.execSQL(CREATE_TABLE_CATALOG_OF_PESTICIDES);
        db.execSQL(CREATE_TABLE_OPERATIONS);
        db.execSQL(CREATE_TABLE_NOTES);

        ToolClass.fillCatalogOfPesticides(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.TradeOfPepperItem.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.OutgoingsItem.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.PesticidesItem.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.PesticidesItem.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.NotesItem.TABLE_NAME);

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
    public Cursor getItemID(String tableName, String columnID, String columnDataPassword, String passwordKey){
        String[] columns={columnID};
        String selection = columnDataPassword+" LIKE ?";
        String[] selection_args= {passwordKey};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(tableName, columns, selection,selection_args,null,null,null);
    }
    public Cursor getItemIDFromName(String tableName, String columnID, String nameFromDB, String nameFromRV){
        String[] columns={columnID};
        String selection = nameFromDB+" LIKE ?";
        String[] selection_args= {nameFromRV};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(tableName, columns, selection,selection_args,null,null,null);
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
        String selection = DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES+" LIKE ?";
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

    public void addNoteItem(String title, String date, String describe, int image) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseNames.NotesItem.COLUMN_TITLE, title);
        values.put(DataBaseNames.NotesItem.COLUMN_DATE, date);
        values.put(DataBaseNames.NotesItem.COLUMN_DESCRIBE, describe);
        values.put(DataBaseNames.NotesItem.COLUMN_IMAGE, image);
        db.insertOrThrow(DataBaseNames.NotesItem.TABLE_NAME,null,values);
    }
}

