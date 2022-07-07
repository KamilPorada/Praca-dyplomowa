package HelperClasses;

import android.content.Context;
import android.database.Cursor;

import java.util.Date;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;

public class StatisticsHelper {

    public static float getMoneyFromTrade(Context context, int year)
    {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getMoneyFromTrade();
        String date;
        double money;
        float sum=0;
        int yearFromDataBase;
        while (k.moveToNext())
        {
            money=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM));
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            yearFromDataBase=ToolClass.getYear(date);
            if(year==yearFromDataBase)
                sum= (float) (sum+money);
        }
        return sum;
    }

    public static float getMoneyFromOutgoings(Context context, int year)
    {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getMoneyFromOutgoings();
        String date;
        double money;
        float sum=0;
        int yearFromDataBase;
        while (k.moveToNext())
        {
            money=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING));
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING));
            yearFromDataBase=ToolClass.getYear(date);
            if(year==yearFromDataBase)
                sum= (float) (sum+money);
        }
        return sum;
    }

    public static float calculateSpecificOutgoing(Context context, String category) {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getMoneyFromSpecificOutgoing(category);
        int currentYear= ToolClass.getActualYear();
        int year;
        float totalMoney=0;
        String date;
        while (k.moveToNext())
        {
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING));
            year=ToolClass.getYear(date);
            if(currentYear==year)
                totalMoney= (float) (totalMoney+k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING)));
        }
        return totalMoney;
    }

    public static double calculateIncomeFromHighgrove(Context context, int year, double divider) {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getMoneyFromTrade();
        double sum=0, money;
        String date;
        while (k.moveToNext())
        {
            money=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM));
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            if(ToolClass.getYear(date)==year)
                sum=sum+money;
        }
        sum=sum/divider;
//        stringTotalMoneyFromTrade=String.format("%.2f", Math.round(totalMoney * 100.0) / 100.0);

        return sum;
    }

    public static double calculateWeightFromHighgrove(Context context, int year, double divider) {
        DataBaseHelper db= new DataBaseHelper(context);
        String date;
        double sum=0;
        Cursor cursor = db.getWeightFromTrade();
        while (cursor.moveToNext())
        {
            date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            if(ToolClass.getYear(date) == year)
                sum=sum+cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
        }
        sum=sum/divider;
        return sum;
    }

    public static double calculateWeightFromColorOfPepper(Context context, int year, String color) {
        DataBaseHelper db = new DataBaseHelper(context);
        String date;
        double sum=0;
        Cursor cursor = db.getWeightFromColor(color);
        while (cursor.moveToNext())
        {
            date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            if(ToolClass.getYear(date) == year)
                sum=sum+cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
        }
        return sum;
    }

    public static double calculateWeightFromClassOfPepper(Context context, int year, String clas) {
        DataBaseHelper db = new DataBaseHelper(context);
        String date;
        double sum=0;
        Cursor cursor = db.getWeightFromClass(clas);
        while (cursor.moveToNext())
        {
            date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            if(ToolClass.getYear(date) ==year)
                sum=sum+cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
        }
        return sum;
    }

    public static double getaveragePriceOfPepper(Context context) {
        double price = calculateIncomeFromHighgrove(context,ToolClass.getActualYear(),1);
        double weight = calculateWeightFromHighgrove(context,ToolClass.getActualYear(),1);
        return price/weight;
    }

    public static double calculateMonthlyWeightFromHighgrove(Context context, int month, int year) {
        DataBaseHelper db= new DataBaseHelper(context);
        String date;
        double sum=0;
        Cursor cursor = db.getWeightFromTrade();
        while (cursor.moveToNext())
        {
            date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            if(ToolClass.getYear(date) == year)
                if(ToolClass.getMonth(date)==month)
                    sum=sum+cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
        }
        return sum;
    }

}
