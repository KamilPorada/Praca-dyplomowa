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
        String result = String.format("%.2f", Math.round(totalMoney * 100.0) / 100.0);
        return totalMoney;
    }
}
