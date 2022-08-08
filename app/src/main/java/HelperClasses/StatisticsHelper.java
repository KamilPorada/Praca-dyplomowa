package HelperClasses;

import android.content.Context;
import android.database.Cursor;

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

    public static double calculateWeightFromColorOfPepper(Context context, int year, int color) {
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

    public static double calculateWeightFromClassOfPepper(Context context, int year, int clas) {
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

    public static double getAveragePriceOfPepper(Context context) {
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

    public static double calculateMonthlyUsagesOfWater(Context context, int month, int year)
    {
        DataBaseHelper db = new DataBaseHelper(context);
        String date;
        String times;
        double efficiency;
        double itemSum=0;
        int status;
        Cursor cursor = db.getWateringPlantationItems();
        while (cursor.moveToNext())
        {
            date = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_DATE));
            times = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_TIMES_OF_EACH_ROUND));
            efficiency = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_EFFICIENCY_OF_PUMP));
            status = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_STATUS));
            if(ToolClass.getYear(date) == year)
                if(ToolClass.getMonth(date)==month)
                    if(status==1)
                        itemSum=itemSum+(ToolClass.sumString(times)*efficiency);
        }
        return itemSum;
    }

}
