package HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.pracadyplomowa.R;

import java.util.Calendar;
import java.util.Date;

import DataBase.DataBaseNames;
import DataBase.SharedPreferencesNames;

public  class ToolClass {


    public static void updateDarkMode()
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
    public static int getActualMonth()
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }
    public static int getActualDay()
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    public static int getActualHour()
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    public static int getActualMinute()
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public static int getYear(String date) {
        char[] charDate = date.toCharArray();
        String stringYear = charDate[6] + Character.toString(charDate[7]) +
                charDate[8] + charDate[9];
        return Integer.parseInt(stringYear);
    }

    public static int getMonth(String date) {
        char[] charDate = date.toCharArray();
        String stringMonth = charDate[3] + Character.toString(charDate[4]);
        return Integer.parseInt(stringMonth);
    }

    public static int getDay(String date) {
        char[] charDate = date.toCharArray();
        String stringMonth = charDate[0] + Character.toString(charDate[1]);
        return Integer.parseInt(stringMonth);
    }

    public static int getHour(String time) {
        char[] charTime = time.toCharArray();
        String stringHour = charTime[0] + Character.toString(charTime[1]);
        return Integer.parseInt(stringHour);
    }

    public static int getMinute(String time) {
        char[] charTime = time.toCharArray();
        String stringMinute = charTime[3] + Character.toString(charTime[4]);
        return Integer.parseInt(stringMinute);
    }

    public static Calendar generateCalendarDate(String date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, ToolClass.getDay(date));
        calendar.set(Calendar.MONTH,ToolClass.getMonth(date)-1);
        calendar.set(Calendar.YEAR,ToolClass.getYear(date));

        return calendar;
    }

    public static Calendar generateCalendarDate(String date,String time)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, ToolClass.getDay(date));
        calendar.set(Calendar.MONTH,ToolClass.getMonth(date)-1);
        calendar.set(Calendar.YEAR,ToolClass.getYear(date));
        calendar.set(Calendar.HOUR_OF_DAY, ToolClass.getHour(time));
        calendar.set(Calendar.MINUTE,ToolClass.getMinute(time));

        return calendar;
    }

    public static Calendar generateCurrentCalendarDate()
    {
        int day = ToolClass.getActualDay();
        int month = ToolClass.getActualMonth();
        int year = ToolClass.getActualYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.HOUR_OF_DAY, ToolClass.getActualHour());
        calendar.set(Calendar.MINUTE,ToolClass.getActualMinute());

        return calendar;
    }

    public static String generateStringDate(Calendar calendar)
    {
        String date="";

        if(calendar.get(Calendar.DAY_OF_MONTH)<10)
            date=date+"0"+calendar.get(Calendar.DAY_OF_MONTH)+".";
        else
            date=date+calendar.get(Calendar.DAY_OF_MONTH)+".";
        if(calendar.get(Calendar.MONTH)<10)
            date=date+"0"+(calendar.get(Calendar.MONTH))+".";
        else
            date=date+(calendar.get(Calendar.MONTH))+".";
        date=date+calendar.get(Calendar.YEAR);

        return date;
    }

    public static String generateStringDate(int day, int month, int year)
    {
        String calendarDate="";
        if(day<10)
            calendarDate=calendarDate+"0"+day+".";
        else
            calendarDate=calendarDate+day+".";
        if(month+1<10)
            calendarDate=calendarDate+"0"+(month+1)+".";
        else
            calendarDate=calendarDate+(month+1)+".";
        calendarDate=calendarDate+year;

        return calendarDate;
    }

    public static String generateCurrentStringDate()
    {
        String date="";
        int day = ToolClass.getActualDay();
        int month = ToolClass.getActualMonth();
        int year = ToolClass.getActualYear();

        if(day<10)
            date=date+"0"+day+".";
        else
            date=date+day+".";
        if(month<10)
            date=date+"0"+month+".";
        else
            date=date+month+".";
        date=date+year;

        return date;
    }

    public static  String generateStringTime(int hour, int minute)
    {
        String clockTime="";
        if(hour<10)
            clockTime=clockTime+"0"+hour+":";
        else
            clockTime=clockTime+hour+":";
        if(minute<10)
            clockTime=clockTime+"0"+minute;
        else
            clockTime=clockTime+minute;

        return clockTime;
    }

    public static boolean compareDateAndTimeWithCurrentDateAndTime(String date, String time)
    {
        Calendar anyDate = ToolClass.generateCalendarDate(date, time);
        Calendar todayDate = ToolClass.generateCurrentCalendarDate();

        return anyDate.after(todayDate);
    }

    public static boolean checkValidateData(String date) {
        boolean validateDay;
        boolean validateMonth;
        boolean validateDotts;

        if(date.length()<10)
           return false;
        else {
            try {
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
            }catch (Exception e)
            {
                return false;
            }
        }
    }

    public static boolean checkValidateHour(String time) {
        boolean validateHour;
        boolean validateMinutes;
        boolean validateColon;

        if (time.length() < 5)
            return false;
        else {
            try {
                char[] charTime = time.toCharArray();
                String stringHour = charTime[0] + Character.toString(charTime[1]);
                String firstDots = Character.toString(charTime[2]);
                String stringMinutes = charTime[3] + Character.toString(charTime[4]);

                int hour = Integer.parseInt(stringHour);
                int minutes = Integer.parseInt(stringMinutes);


                validateHour = hour >= 0 && hour < 24;
                validateMinutes = minutes >= 0 && minutes < 60;
                validateColon = firstDots.compareTo(":") == 0;

                return validateHour && validateMinutes && validateColon;
            }catch (Exception e)
            {
                return false;
            }
        }
    }

    public static int substringCalendarDates(Calendar date1, Calendar date2)
    {
        int [] dayInMonths={31,28,31,30,31,30,31,31,30,31,30,31};
        int daysDate1=0, daysDate2=0;
        int monthDate1=date1.get(Calendar.MONTH);
        int monthDate2=date2.get(Calendar.MONTH);

        for(int i=0;i<monthDate1;i++)
            daysDate1=daysDate1+dayInMonths[i];
        daysDate1=daysDate1+date1.get(Calendar.DAY_OF_MONTH);

        for(int i=0;i<monthDate2;i++)
            daysDate2=daysDate2+dayInMonths[i];
        daysDate2=daysDate2+date2.get(Calendar.DAY_OF_MONTH);

        return daysDate1-daysDate2;
    }

    public static boolean checkValidateYear(String data) {
        char[] charDate = data.toCharArray();
        String stringYear = charDate[6] + Character.toString(charDate[7]) +
                charDate[8] + charDate[9];
        int year = Integer.parseInt(stringYear);
        System.out.println(String.valueOf(year) + getActualYear());
        return year == getActualYear();
    }

    public static double getHighgroves(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.FarmData.NAME, Context.MODE_PRIVATE);
        String highgroves = sharedPreferences.getString(SharedPreferencesNames.FarmData.HIGHGROVES,"0");
        return Double.parseDouble(highgroves);
    }

    public static int getAreaOfPlantation(int highgroves)
    {
        return 32*8*highgroves;
    }

    public static int getHerbicideAreaOfPlantation(int highgroves)
    {
        return (32*2+ 8)*highgroves;
    }

    public static void fillCatalogOfPesticides(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("ABAMAX 018 EC", "Przędziorek chmielowiec, szklarniowiec",0,0.05,3,3,"Opryskiwać rośliny po zauważeniu szkodnika lub pierwszych objawów uszkodzeń. Jeżeli w poprzednich zesonach nie było problemu ze szkodnikiem, zabieg można ograniczyć do ogniska wystąpienia pierwszych osobników.",R.drawable.image_pesticide_abamax));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("ACARAMIK 018 EC", "Przędziorek chmielowiec, szklarniowiec",0,0.05,3,3,"Opryskiwać rośliny po zauważeniu szkodnika lub pierwszych objawów uszkodzeń. Jeżeli w poprzednich zesonach nie było problemu ze szkodnikiem, zabieg można ograniczyć do ogniska wystąpienia pierwszych osobników.",R.drawable.image_pesticide_acaramik));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("ACEPTIR 200 SE", "Mszyce ",0,0.3,2,3,"Po posadzeniu roślin, w celu monitorowania obecności i liczebności tych owadów wywieszać żółte tablice lepowe.",R.drawable.image_pesticide_aceptir));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("AFFIRM 095 SG", "Światłówka naziemnica , błyszczka jarzynówka",0,1.5,1,3,"Opryskiwać do 2 razy w sezonie, najpóźniej w fazie pełnej dojrzałości rośliny uprawnej.",R.drawable.image_pesticide_affirm));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("AGRICOLLE", "Przędziorek chmielowiec, szklarniowiec",0,0.4,3,0,"Preparaty wspomagające ochronę, działają mechanicznie i fizycznie. Dokładnie pokryć roztworem chronioną powierzchnię, także dolna strone liści.",R.drawable.image_pesticide_agricolle));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("APIS 200 SE", "Mszyce ",0,0.3,2,1,"Środek powierzchniow, wgłębny i układowy. Zwalcza się nim wszystkie stadai ruchome tego szkodnika, w tym larwy żerujące wewnątrz rośliny.",R.drawable.image_pesticide_apis));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("BIOBIT", "Światłówka naziemnica , błyszczka jarzynówka",0,0.8,1,0,"Opryskiwać do 3 razy w sezonie, w trakcie lub bezpośrednio po wylęgu gąsienic, najlepiej wieczorem. Po pobraniu środka, gąsienice przestają żerować, mogą pozostac jeszcze żywe przez kilka dni.",R.drawable.image_pesticide_biobit));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("DIPEL WG", "Światłówka naziemnica , błyszczka jarzynówka",0,0.8,1,0,"Opryskiwać do 3 razy w sezonie, w trakcie lub bezpośrednio po wylęgu gąsienic, najlepiej wieczorem. Po pobraniu środka, gąsienice przestają żerować, mogą pozostac jeszcze żywe przez kilka dni..",R.drawable.image_pesticides_dipel));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("EMULPAR 940 EC", "Wciornastek tytoniowiec i zachodni",0,1.2,3,0,"Preparat wspomagający ochronę przed mało ruchliwymi larwami mączlików, działają mechanicznie i fizycznie. Dokładnie pokryć roztworem chronioną powierzchnię.",R.drawable.image_pesticide_empular));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("ERADICOAT MAX", "Mączlik szklarniowy i ostroskrzydły",0,0.2,3,1,"Stosować 2-20 razy w sezonie, z zachowaniem minimum  3 dni między zabiegami.",R.drawable.image_pesticide_eradicoat));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("FLORAMITE 240 SC", "Przędziorek chmielowiec, szklarniowiec", 0,0.04,3,1,"Opryskiwać rośliny po zauważeniu szkodnika lub pierwszych objawów uszkodzeń. Jeżeli w poprzednich zesonach nie było problemu ze szkodnikiem, zabieg można ograniczyć do ogniska wystąpienia pierwszych osobników.",R.drawable.image_pesticide_floramite));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("KOBE 20 SP", "Mszyce ",0,0.04,3,14,"Po posadzeniu roślin, w celu monitorowania obecności i liczebności tych owadów wywieszać żółte tablice lepowe.",R.drawable.image_pesticide_kobe));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("LANMOS 20 SP", "Wciornastek tytoniowiec i zachodni",0,0.04,3,14,"Po posadzeniu roślin, w celu monitorowania obecności i liczebności tych owadów wywieszać niebieskie tablice lepowe. Opryskiwać bezpośrednio po stwierdzeniu szkodnika lub po uszkodzeń na roślinach.",R.drawable.image_pesticide_lanmos));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("LIMOCIDE", "Mączlik szklarniowy i ostroskrzydły",0,2,2,1,"Opryskiwać do 6 razy w sezonie, od 2 liścia do fazy pełnej dojrzałości.",R.drawable.image_pesticide_limocide));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("LOS OVADOS 200 SE", "Wciornastek tytoniowiec i zachodni",0,0.3,2,3,"Po posadzeniu roślin, w celu monitorowania obecności i liczebności tych owadów wywieszać niebieskie tablice lepowe. Opryskiwać bezpośrednio po stwierdzeniu szkodnika lub po uszkodzeń na roślinach.",R.drawable.image_pesticide_losovados));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("MOSPILAN 20 SP", "Mszyce ",0,0.4,3,14,"Po posadzeniu roślin, w celu monitorowania obecności i liczebności tych owadów wywieszać żółte tablice lepowe.",R.drawable.image_pesticide_mospilan));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("NATURALIS", "Przędziorek chmielowiec, szklarniowiec",0,2,2,0,"Opryskiwać do 5 razy w sezonie. Konidia grzyba przylegają do naskórka roztoczy i wnikają do wnętrza ciała gospodarza. Śmierć następuje w wyniku mechanicznej penetracji grzybni.", R.drawable.image_pesticide_naturalis));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("NISSORUN 250 SC", "Przędziorek chmielowiec, szklarniowiec", 0,0.03,2,3,"Opryskiwać rośliny po zauważeniu szkodnika lub pierwszych objawów uszkodzeń. Jeżeli w poprzednich zesonach nie było problemu ze szkodnikiem, zabieg można ograniczyć do ogniska wystąpienia pierwszych osobników.",R.drawable.image_pesticide_nissorun));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("ORTUS 05 SC", "Przędziorek chmielowiec, szklarniowiec", 0,0.01,3,7,"Opryskiwać rośliny po zauważeniu szkodnika lub pierwszych objawów uszkodzeń. Jeżeli w poprzednich zesonach nie było problemu ze szkodnikiem, zabieg można ograniczyć do ogniska wystąpienia pierwszych osobników.",R.drawable.image_pesticide_ortus));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("PESTICOL", "Mączlik szklarniowy i ostroskrzydły",0,2,2,1,"Opryskiwać do 6 razy w sezonie, od 2 liścia do fazy pełnej dojrzałości.",R.drawable.image_pesticide_pesticol));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("PREFERAL", "Mączlik szklarniowy i ostroskrzydły",0,0.001,3,0,"Opryskiwać do 3 razy w sezonie. Blastospory grzyba wnikają do kutikuli owada. Grzyb rozwija się wewnątrz ciała mączlika, powodując jego śmierć w ciągu 7-10 dni.",R.drawable.image_pesticide_preferal));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("REQUIEM PRIME", "Przędziorek chmielowiec, szklarniowiec",0,0.65,3,0,"Stosować do 12 razy w sezonie od pierwszego rozwoju liścia aż do pełnej dojrzałości owoców. Działa kontaktowo, gazowo i repelentnie. Z powodu przemijającej fitotoksyczności stosować od początku maja do końca września.",R.drawable.image_pesticide_requiemprime));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("SAFRAN 018 EC", "Przędziorek chmielowiec, szklarniowiec",0,0.05,3,3,"Opryskiwać rośliny po zauważeniu szkodnika lub pierwszych objawów uszkodzeń. Jeżeli w poprzednich zesonach nie było problemu ze szkodnikiem, zabieg można ograniczyć do ogniska wystąpienia pierwszych osobników.",R.drawable.image_pesticide_safran));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("SEKIL 20 SP", "Mączlik szklarniowy i ostroskrzydły",0,0.04,3,14,"Po posadzeniu roślin w celu monitorowania obecności i liczebności tych owadów wywieszać żółte tablice lepowe. Zabieg wykonywać bezpośrednio po stwierdzeniu szkodnika.",R.drawable.image_pesticide_sekil));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("SPINTOR 240 SC", "Miniarki ",0,0.4,2,3,"Środek ten działa powierzchniowo, a w przypadku młodych liści - także wgłębnie. Opryskiwać do 3 razy w sezonie, od początku kwitnienia do stadium, gdy 70% owoców uzyskuję barwę typową dla odmiany.",R.drawable.image_pesticide_spintor));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("SEQUOIA", "Mszyce ",0,0.2,2,1,"Środek układowy i translaminarny. Stosować od fazy tworzenia 1 pędu do dojrzewania owoców, w szklarniach o trwałej konstrukcji, odizolowanej od podłoża.",R.drawable.image_pesticide_sequoia));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("VERTIGO 018 EC", "Przędziorek chmielowiec, szklarniowiec",0,0.05,3,3,"Opryskiwać rośliny po zauważeniu szkodnika lub pierwszych objawów uszkodzeń. Jeżeli w poprzednich zesonach nie było problemu ze szkodnikiem, zabieg można ograniczyć do ogniska wystąpienia pierwszych osobników.",R.drawable.image_pesticide_vertigo));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("VERTIMEC 018 EC", "Przędziorek chmielowiec, szklarniowiec",0,0.05,3,3,"Opryskiwać rośliny po zauważeniu szkodnika lub pierwszych objawów uszkodzeń. Jeżeli w poprzednich sezonach nie było problemu ze szkodnikiem, zabieg można ograniczyć do ogniska wystąpienia pierwszych osobników.",R.drawable.image_pesticide_vertimec));

        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("AMISTAR 250 SC", "Szara pleśń",1,0.1,3,3,"Kontrolować rośliny systematycznie, zwłaszcza na tych stanowiskach, na których choroba występowała w poprzednim sezonie. Opryskiwać rośliny po stwierdzeniu objawów. Zabieg wykonywać co 7-10 dni.",R.drawable.image_pesticide_amistar));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("AMYLO X-WG", "Mączniak prawdziwy",1,2,1,0,"Środek stosować do 6 razy w sezonie, od fazy liścieni całkowicie rozwiniętych do pełnej dojrzałości owoców.",R.drawable.image_pesticide_amylox));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("ARMICARB SP", "Mączniak prawdziwy",1,0.3,3,0,"Opryskiwać do 6 razy w sezonie, zapobiegawczo od fazy rozwoju liści do fazy pełnej dojrzałości owoców..",R.drawable.image_pesticide_armicarb));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("AZARIUS 250 SC", "Zgnilizna twardzikowa",1,0.1,3,3,"Kontrolować rośliny systematycznie, zwłaszcza na tych stanowiskach, na których choroba występowała w poprzednim sezonie. Opryskiwać rośliny po stwierdzeniu objawów. Zabieg wykonywać co 7-10 dni.",R.drawable.image_pesticide_azarius));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("BACTERPLANT", "Fytoftoroza, zgorzel łodyg, fuzarioza, szara pleśń",1,0.8,1,0,"Preparat wspomagający. Zawiera niepatogennne bakterie antagonistyczne wobec sprawców chorób.",R.drawable.image_pesticide_bacterplant));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("DAGONIS", "Mączniak prawdziwy",1,0.6,2,3,"Opryskiwać od wystąpienia rozwiniętego liścia właściwego na pędzie głównym do końca fazy, gfy owoce mają typową barwę.",R.drawable.image_pesticide_dagonis));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("DISCUS 500 WG", "Mączniak prawdziwy",1,0.5,1,3,"Opryskiwać od wystąpienia rozwiniętego liścia właściwego na pędzie głównym do końca fazy, gfy owoce mają typową barwę.",R.drawable.image_pesticide_discus));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("DOBROMIR 250 SC", "Zgnilizna twardzikowa",1,0.1,3,3,"Kontrolować rośliny systematycznie, zwłaszcza na tych stanowiskach, na których choroba występowała w poprzednim sezonie. Opryskiwać rośliny po stwierdzeniu objawów. Zabieg wykonywać co 7-10 dni.",R.drawable.image_pesticide_dobromir));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("FYTOSAVE SL", "Mączniak prawdziwy",1,0.2,3,0,"Opryskiwać do 5 razy w sezonie, głównie zapobiegawczo.",R.drawable.image_pesticide_fytosave));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("GEOXE 50 WG", "Szara pleśń",1,0.5,1,3,"Stosować do 2 razy w sezonie, od fazy widocznego pąka kwiatowego do fazy, gdy owoce osiągną pełną dojrzałość.",R.drawable.image_pesticide_geoxe));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("GLOBAZTAR 250 SC", "Zgnilizna twardzikowa",1,0.1,3,3,"Kontrolować rośliny systematycznie, zwłaszcza na tych stanowiskach, na których choroba występowała w poprzednim sezonie. Opryskiwać rośliny po stwierdzeniu objawów. Zabieg wykonywać co 7-10 dni.",R.drawable.image_pesticide_globaztar));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("JULIETTA", "Szara pleśń",1,2.5,1,0,"Środek stosować zapobiegawczo, max. 8 razy w sezonie, co 7-14 dni, w zależności od poziomu zagrożenia chorobą.",R.drawable.image_pesticide_julietta));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("LUNA SENSATION", "Szara pleśń",1,0.6,2,3,"Stosować do 2 razy w cyklu uprawowym, od fazy gdy, widoczny jest 1 pąk kwiatowy do pełnej dojrzałości owoców.",R.drawable.image_pesticide_lunasensation));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("MIRADOR 250 SC", "Zgnilizna twardzikowa",1,0.1,3,3,"Kontrolować rośliny systematycznie, zwłaszcza na tych stanowiskach, na których choroba występowała w poprzednim sezonie. Opryskiwać rośliny po stwierdzeniu objawów. Zabieg wykonywać co 7-10 dni.",R.drawable.image_pesticide_mirodor));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("NORDOX 75 WG", "Antraknoza ,fytoftoroza",1,1.33,1,10,"Opryskiwać do 3 razy w sezonie, od fazy rozwinietych liści właściwych do  początku dojrzewania owoców..",R.drawable.image_pesticide_nordox));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("ORTIVA TOP 235 SC", "Alternarioza",1,1,2,3,"Opryskiwać do 2 razy w sezonie, od fazy gdy widoczny jest 1 kwiatostan do pełnej dojrzałości.",R.drawable.image_pesticide_ortiva));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("PROLECTUS 50 WG", "Szara pleśń",1,0.8,3,1,"Unikaać nadmiernej wilgotności w otoczeniu roślin. Wietrzyć obiekty. Opryskiwać obiekty bezpośrenio po stwierdzeniu objawów choroby. Zabieg powtarzać co 7-10 dni.",R.drawable.image_pesticide_proctelus));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("SCALA", "Szara pleśń",1,2,2,3,"Środek stosować max. 2 razy w sezonie, od początku fazy rozwoju kwiatostanu, gdy widoczny jest pierwszy pąk kwiatowy do uzyskania pełnej dojrzałości owowców.",R.drawable.image_pesticide_scala));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("SCORPION 325 SC", "Brunatna plamistość",1,1,2,3,"Opryskiwać do 2 razy w sezonie, od widocznego 1 będu bocznego na pędzie głównym do stadium, gdy 70 % owoców uzyskuję typową barwę.",R.drawable.image_pesticide_scorpion));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("SERENADE ASO", "Bakteryjna cętkowatość",1,8,2,0,"Bacillus subtilis zawarta w tym środku jest bakterią zakłócającą rozwój grzybni i wytwarzającą substancję, które zaburzają funkcjonowanie błon komórkowych.",R.drawable.image_pesticide_serenade));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("SERIFEL", "Szara pleśń",1,0.5,1,0,"Stosować do 6 razy w sezonie do pełnej dojrzałości owoców.",R.drawable.image_pesticide_serifel));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("SIGNUM 33 WG", "Zgnilizna twardzikowa",1,1.5,1,3,"Kontrolować rośliny systematycznie, zwłaszcza na tych stanowiskach, na których choroba występowała w poprzednim sezonie. Opryskiwać rośliny po stwierdzeniu objawów. Zabieg wykonywać co 7-10 dni.",R.drawable.image_pesticide_signum));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("SWITCH 62,5 WG", "Zgnilizna twardzikowa",1,1,1,3,"Kontrolować rośliny systematycznie, zwłaszcza na tych stanowiskach, na których choroba występowała w poprzednim sezonie. Opryskiwać rośliny po stwierdzeniu objawów. Zabieg wykonywać co 7-10 dni.",R.drawable.image_pesticide_switch));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("TAEGRO", "Mączniak prawdziwy",1,0.3,2,0,"Środek stosować zapobiegawczo, od końca fazy rozwoju liści - co najmniej 9 rozwiniętych liści na pędzie głównym do końca fazy dojrzewania. Stosować do 12 razy w sezonie w odstępach 3 dni od zabiegu.",R.drawable.image_pesticide_taegro));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("TOPAS 100 EC", "Mączniak prawdziwy",1,0.5,2,3,"Opryskiwać od wystąpienia rozwiniętego liścia właściwego na pędzie głównym do końca fazy, gfy owoce mają typową barwę.",R.drawable.image_pesticide_topas));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("TOPSIN M 500 SC", "Werticiloza",1,0.15,3,3,"Opryskiwać profilaktycznie (na stanowiskach, na których w poprzednim sezonie uprawowym stwierdzono chorobę) lub interwencyjnie (po stwierdzeniu symptomów).", R.drawable.image_pesticide_topsin));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("VIVANDO", "Mączniak prawdziwy",1,0.2,2,3,"Opryskiwać do 2 razy w sezonie, co 7-10 dni, od rozwiniętego 1 liścia właściwego na pędzie głónym do fazy pełnej dojrzałości.",R.drawable.image_pesticide_vivando));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("ZAFTRA 250 SC", "Zgnilizna twardzikowa",1,0.1,3,3,"Kontrolować rośliny systematycznie, zwłaszcza na tych stanowiskach, na których choroba występowała w poprzednim sezonie. Opryskiwać rośliny po stwierdzeniu objawów. Zabieg wykonywać co 7-10 dni.",R.drawable.image_pesticide_zaftra));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("ZAKEO 250 SC", "Zgnilizna twardzikowa",1,0.1,3,3,"Kontrolować rośliny systematycznie, zwłaszcza na tych stanowiskach, na których choroba występowała w poprzednim sezonie. Opryskiwać rośliny po stwierdzeniu objawów. Zabieg wykonywać co 7-10 dni.",R.drawable.image_pesticide_zakeo));

        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("FUSILADE 150 EC", "Chwasty roczne jednoliścienne",2,0.7,2,0,"Fusilade forte 150 EC jest herbicydem, w formie koncentratu do sporządzania emulsji wodnej, stosowanym nalistnie, przeznaczonym do zwalczania perzu właściwego oraz rocznych chwastów jednoliściennych.",R.drawable.image_pesticide_fusillade));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("ORKAN 350 SL", "Chwasty roczne jednoliścienne oraz wieloletnie dwuliścienne",2,5,2,0,"Orkan 350 SL jest środkiem chwastobójczym, w formie płynu do sporządzania roztworu wodnego, stosowanym nalistnie, przeznaczonym do zwalczania rocznych i wieloletnich chwastów jednoliściennych i dwuliściennych.",R.drawable.image_pesticide_orkan));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("ROUNDUP 360 SL", "Chwasty roczne oraz wieloletnie",2,2.5,2,0,"Roundup 360 Plus to środek chwastobójczy do stosowania nalistnego, glifosat, zwalcza perz właściwy oraz roczne i wieloletnie chwasty jednoliścienne i dwuliścienne.",R.drawable.image_pesticide_roundup));
        sqLiteDatabase.execSQL(createPesticideItemInsertQuery("STOMP AQUA 455 CS", "Chwasty jednoliścienne",2,20,2,0,"Stomp Aqua 455 CS jest pobierany przez korzenie i części nadziemne chwastów. Najskuteczniej zwalcza chwasty w okresie ich kiełkowania i wschodów. Chwasty jednoliścienne są zwalczane do fazy pierwszego lub do początku drugiego liścia, a chwasty dwuliścienne do fazy dwóch liści właściwych.",R.drawable.image_pesticide_stomp));

    }

    private static String createPesticideItemInsertQuery(String name, String pest, int typeOfPesticide, double dose, int typeOfDose,
                                                         int grace, String notes, int image) {
        return "INSERT INTO " + DataBaseNames.PesticidesItem.TABLE_NAME + "(" +
                DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES + ", " +
                DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PEST + ", " +
                DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_PESTICIDE + ", " +
                DataBaseNames.PesticidesItem.COLUMN_DOSE + ", " +
                DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_DOSE + ", " +
                DataBaseNames.PesticidesItem.COLUMN_OF_GRACE + ", " +
                DataBaseNames.PesticidesItem.COLUMN_NOTES + ", " +
                DataBaseNames.PesticidesItem.COLUMN_IMAGE + ") VALUES (" +
                "'" + name + "'" + ", " + "'" + pest + "'" + ", " + "'" + typeOfPesticide + "'" + ", " + "'" + dose + "'" + ", " +
                "'" + typeOfDose + "'" + ", " + "'" + grace + "'" + ", " + "'" + notes + "'" + ", " + "'" + image + "'" + ");";
    }

    public static int getPepperDrawable(int color)
    {
        switch (color)
        {
            case 0:
                return R.drawable.image_red_pepper;
            case 1:
                return R.drawable.image_yellow_pepper;
            case 2:
                return R.drawable.image_green_pepper;
            case 3:
                return R.drawable.image_orange_pepper;
            case 4:
                return R.drawable.image_blond_pepper;
            default:
                return 0;
        }
    }

    public static void clearTemporaryCurrentOperations(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.TemporaryCurrentOperations.NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SharedPreferencesNames.TemporaryCurrentOperations.TYPE_OF_PESTICIDES, 0);
        editor.putString(SharedPreferencesNames.TemporaryCurrentOperations.DATA_OF_OPERATIONS, "");
        editor.putString(SharedPreferencesNames.TemporaryCurrentOperations.HOUR_OF_OPERATIONS, "");
        editor.putInt(SharedPreferencesNames.TemporaryCurrentOperations.AMOUNT_OF_HIGHGROVES, 0);
        editor.putString(SharedPreferencesNames.TemporaryCurrentOperations.PESTICIDES, "");
        editor.putInt(SharedPreferencesNames.TemporaryCurrentOperations.ID_OF_PESTICIDES, 0);
        editor.apply();
    }


    public static int getOutgoingsDrawable(int idOfCategory) {
        switch (idOfCategory)
        {
            case 0:
                return R.drawable.image_highgrove;
            case 1:
                return R.drawable.image_foil;
            case 2:
                return R.drawable.image_water;
            case 3:
                return R.drawable.image_pegs;
            case 4:
                return R.drawable.image_seeds;
            case 5:
                return R.drawable.image_plant;
            case 6:
                return R.drawable.image_bottle_of_pesticides;
            case 7:
                return R.drawable.image_fertilizer;
            case 8:
                return R.drawable.icon_machine;
            case 9:
                return R.drawable.image_tools;
            case 10:
                return R.drawable.icon_question;
            default:
                return R.drawable.image_outgoings;
        }
    }
}
