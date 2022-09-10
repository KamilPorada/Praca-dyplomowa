package DataBase;

import android.provider.BaseColumns;

public class DataBaseNames {
    public static final class TradeOfPepperItem implements BaseColumns {
        public static final String TABLE_NAME = "tradeOfPepperItem";
        public static final String COLUMN_COLOR_OF_PEPPER = "colorOfPepper";
        public static final String COLUMN_VAT = "vat";
        public static final String COLUMN_CLASS_OF_PEPPER = "classOfPepper";
        public static final String COLUMN_PRICE_OF_PEPPER = "priceOfPepper";
        public static final String COLUMN_WEIGHT_OF_PEPPER = "weightOfPepper";
        public static final String COLUMN_TOTAL_SUM = "totalSum";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_PLACE = "place";
    }

    public static final class OutgoingsItem implements BaseColumns{
        public static final String TABLE_NAME = "outgoingsItem";
        public static final String COLUMN_ID_OF_CATEGORY = "idOfCategory";
        public static final String COLUMN_NAME = "nameOfOutgoing";
        public static final String COLUMN_DESCRIBE_OF_OUTGOING = "describeOfOutgoing";
        public static final String COLUMN_PRICE_OF_OUTGOING = "priceOfOutgoing";
        public static final String COLUMN_DATE_OF_OUTGOING = "dateOfOutgoing";

    }
    public static final class PesticidesItem implements BaseColumns{
        public static final String TABLE_NAME = "pesticideItem";
        public static final String COLUMN_NAME_OF_PESTICIDES = "nameOfPesticides";
        public static final String COLUMN_NAME_OF_PEST = "nameOfPest";
        public static final String COLUMN_TYPE_OF_PESTICIDE = "typeOfPesticides";
        public static final String COLUMN_DOSE = "dose";
        public static final String COLUMN_TYPE_OF_DOSE = "typeOfDose";
        public static final String COLUMN_OF_GRACE = "grace";
        public static final String COLUMN_NOTES = "notes";
        public static final String COLUMN_IMAGE = "image";
    }
    public static final class OperationsItem implements BaseColumns{
        public static final String TABLE_NAME = "operationsItem";
        public static final String COLUMN_ID_PESTICIDE = "IdPesticide";
        public static final String COLUMN_DATE = "operationDate";
        public static final String COLUMN_TIME = "operationTime";
        public static final String COLUMN_DATE_END_OF_GRACE = "operationDateEndOfGRace";
        public static final String COLUMN_AGE_OF_PEPPER = "ageOfPepper";
        public static final String COLUMN_HIGHGROVES = "highgroves";
        public static final String COLUMN_FLUID = "fluid";
        public static final String COLUMN_STATUS = "status";
    }
    public static final class NotesItem implements  BaseColumns{
        public static final String TABLE_NAME = "notesItem";
        public static final String COLUMN_TITLE = "titleNote";
        public static final String COLUMN_DATE = "dateNote";
        public static final String COLUMN_DESCRIBE = "describeNote";
        public static final String COLUMN_IMAGE = "imageNote";
    }
    public static final class WaterPlantationItem implements  BaseColumns{
        public static final String TABLE_NAME = "waterPlantationItem";
        public static final String COLUMN_EFFICIENCY_OF_PUMP = "efficiencyOfPump";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_AMOUNT_OF_HIGHGROVES_IN_EACH_ROUND = "amountOfHighhgrovesInEachRound";
        public static final String COLUMN_TIMES_OF_EACH_ROUND = "timesOfEachRound";
        public static final String COLUMN_AMOUNT_OF_ROUND = "COLUMN_AMOUNT_OF_ROUND";
        public static final String COLUMN_STATUS = "status";
    }

    public static final class LocationItem implements BaseColumns{
        public static final String TABLE_NAME = "locationItem";
        public static final String COLUMN_NAME_OF_LOCATION = "nameOfLocation";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
    }

    public static final class MarketItem implements BaseColumns{
        public static final String TABLE_NAME = "marketItem";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_NUMBER = "number";
    }
}
