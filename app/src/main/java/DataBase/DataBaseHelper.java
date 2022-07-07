package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER + " TEXT NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_VAT + " INTEGER NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER + " TEXT NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER + " REAL NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER + " REAL NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM + " REAL NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_DATE + " TEXT NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_PLACE + " TEXT NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_IMAGE + " INTEGER NOT NULL," +
                DataBaseNames.TradeOfPepperItem.COLUMN_DATA_PASWORD + " TEXT NOT NULL" +
                ");";

        final String CREATE_TABLE_OUTGOINGS = "CREATE TABLE " +
                DataBaseNames.OutgoingsItem.TABLE_NAME + " (" +
                DataBaseNames.OutgoingsItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataBaseNames.OutgoingsItem.COLUMN_CATEGORY_OF_OUTGOING + " TEXT NOT NULL," +
                DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING + " TEXT NOT NULL," +
                DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING + " REAL NOT NULL," +
                DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING + " TEXT NOT NULL," +
                DataBaseNames.OutgoingsItem.COLUMN_IMAGE + " INTEGER NOT NULL," +
                DataBaseNames.OutgoingsItem.COLUMN_DATA_PASWORD + " TEXT NOT NULL" +
                ");";

        db.execSQL(CREATE_TABLE_TRADE_OF_PEPPER_ITEM);
        db.execSQL(CREATE_TABLE_OUTGOINGS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.TradeOfPepperItem.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.OutgoingsItem.TABLE_NAME);

        onCreate(db);
    }

    //----------------------------TRADE_OF_PEPPER SQLITE QUERIES-------------------------------//

    public void addTradeOfPepperItem(String color, int vat, String clas, double price, double weight, double sum,
                                     String date, String place, int image, String dataPassword) {
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
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_IMAGE, image);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_DATA_PASWORD, dataPassword);

        db.insertOrThrow(DataBaseNames.TradeOfPepperItem.TABLE_NAME,null,values);
    }
    public void updateTradeOfTradeOfPepperItems(String passwordKey, String color, int vat,
                                                String clas, double price, double weight, double sum,
                                                String date, int image, String place) {
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER,color);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_VAT,vat);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER,clas);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER,price);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER,weight);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM,sum);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_DATE,date);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_IMAGE,image);
        values.put(DataBaseNames.TradeOfPepperItem.COLUMN_PLACE,place);

        String[] args ={passwordKey+""};
        db.update(DataBaseNames.TradeOfPepperItem.TABLE_NAME,values,"dataPassword=?",args);
    }

    public Cursor getTradeOfPepperItems(){
        String[] columns={DataBaseNames.TradeOfPepperItem._ID, DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER,
                          DataBaseNames.TradeOfPepperItem.COLUMN_VAT, DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER,
                          DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER, DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER,
                          DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM,DataBaseNames.TradeOfPepperItem.COLUMN_DATE,
                          DataBaseNames.TradeOfPepperItem.COLUMN_PLACE,DataBaseNames.TradeOfPepperItem.COLUMN_IMAGE,
                          DataBaseNames.TradeOfPepperItem.COLUMN_DATA_PASWORD};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.TradeOfPepperItem.TABLE_NAME,columns, null,null,null,null,null);
    }

    //-----------------------------OUTGOINGS SQLITE QUERIES---------------------------------//

    public void addOutgoing(String category,String describe, double price, String date, int image, String passwordKey) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseNames.OutgoingsItem.COLUMN_CATEGORY_OF_OUTGOING,category);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING,describe);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING,price);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING,date);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_IMAGE,image);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_DATA_PASWORD,passwordKey);

        db.insertOrThrow(DataBaseNames.OutgoingsItem.TABLE_NAME,null,values);
    }

    public void updateOutgoingItems(String passwordKey, String category,String describe, double price, int image, String date) {
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(DataBaseNames.OutgoingsItem.COLUMN_CATEGORY_OF_OUTGOING,category);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING,describe);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING,price);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_IMAGE,image);
        values.put(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING,date);

        String[] args ={passwordKey+""};
        db.update(DataBaseNames.OutgoingsItem.TABLE_NAME,values,"dataPassword=?",args);
    }

    public Cursor getOutgoingItems(){
        String[] columns={DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING, DataBaseNames.OutgoingsItem.COLUMN_CATEGORY_OF_OUTGOING,
                DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING, DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING,
                DataBaseNames.OutgoingsItem.COLUMN_IMAGE,DataBaseNames.OutgoingsItem.COLUMN_DATA_PASWORD};
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

    //------------------------------STATISTICS SQLITE QUERIES---------------------------------//

    public Cursor getWeightFromColor(String color){
        String[] columns={DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER,
                          DataBaseNames.TradeOfPepperItem.COLUMN_DATE};
        String selection = DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER+" LIKE ?";
        String[] selection_args= {color};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.TradeOfPepperItem.TABLE_NAME, columns, selection,selection_args,null,null,null);
    }

    public Cursor getWeightFromClass(String clas){
        String[] columns={DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER,
                DataBaseNames.TradeOfPepperItem.COLUMN_DATE};
        String selection = DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER+" LIKE ?";
        String[] selection_args= {clas};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.TradeOfPepperItem.TABLE_NAME, columns, selection,selection_args,null,null,null);
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
    public Cursor getPriceWeightAndDateFromTrade(String color, String clas){
        String[] columns={DataBaseNames.TradeOfPepperItem.COLUMN_DATE, DataBaseNames.TradeOfPepperItem.COLUMN_VAT,
                          DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER, DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER};
        String selection = DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER + " LIKE ? AND " +
                           DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER + " LIKE ?";
        String[] selection_args= {color, clas};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.TradeOfPepperItem.TABLE_NAME, columns, selection,selection_args,null,null,null);
    }


    public Cursor getMoneyFromSpecificOutgoing(String category){
        String[] columns={DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING, DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING};
        String selection = DataBaseNames.OutgoingsItem.COLUMN_CATEGORY_OF_OUTGOING + " LIKE ?";
        String[] selection_args= {category};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.OutgoingsItem.TABLE_NAME, columns, selection,selection_args,null,null,null);
    }
    public Cursor getMoneyFromOutgoings(){
        String[] columns={DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING, DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING};
        SQLiteDatabase db =getReadableDatabase();
        return db.query(DataBaseNames.OutgoingsItem.TABLE_NAME,columns, null,null,null,null,null);
    }
}
