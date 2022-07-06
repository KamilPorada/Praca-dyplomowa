package OthersClass;

import androidx.appcompat.app.AppCompatDelegate;


import com.example.pracadyplomowa.R;

import java.util.Calendar;
import java.util.Date;

public  class ToolClass {
    public static void updateMode()
    {
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static int getActualYear()
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getYear(String date) {
        char[] charDate = date.toCharArray();
        String stringYear = charDate[6] + Character.toString(charDate[7]) +
                charDate[8] + charDate[9];
        return Integer.parseInt(stringYear);
    }

    public static boolean checkValidateData(String date) {
        boolean validateDay;
        boolean validateMonth;
        boolean validateDotts;


        if(date.length()<10)
           return false;
        else {

            char[] charDate = date.toCharArray();
            String stringDay = charDate[0] + Character.toString(charDate[1]);
            String firstDots = Character.toString(charDate[2]);
            String stringMonth = charDate[3] + Character.toString(charDate[4]);
            String secondDots = Character.toString(charDate[5]);

            int day = Integer.parseInt(stringDay);
            int month = Integer.parseInt(stringMonth);


            validateDay = day > 0 && day < 32;
            validateMonth = month > 0 && month < 13;
            validateDotts = firstDots.compareTo(".") == 0 && secondDots.compareTo(".") == 0;


            return validateDay && validateMonth && validateDotts;
        }

    }

    public static boolean checkValidateYear(String data) {
        char[] charDate = data.toCharArray();
        String stringYear = charDate[6] + Character.toString(charDate[7]) +
                charDate[8] + charDate[9];
        int year = Integer.parseInt(stringYear);
        System.out.println(String.valueOf(year) + getActualYear());
        return year == getActualYear();
    }

    public static int getDrawable(String color)
    {
        switch (color)
        {
            case "czerwona":
                return R.drawable.image_red_pepper;
            case "żółta":
                return R.drawable.image_yellow_pepper;
            case "zielona":
                return R.drawable.image_green_pepper;
            case "pomarańczowa":
                return R.drawable.image_orange_pepper;
            case "blondyna":
                return R.drawable.image_blond_pepper;
            default:
                return R.drawable.image_red_pepper;
        }
    }

}
