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
        public static final String COLUMN_CATEGORY_OF_OUTGOING = "categoryOfOutgoing";
        public static final String COLUMN_DESCRIBE_OF_OUTGOING = "describeOfOutgoing";
        public static final String COLUMN_PRICE_OF_OUTGOING = "priceOfOutgoing";
        public static final String COLUMN_DATE_OF_OUTGOING = "dateOfOutgoing";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DATA_PASWORD = "dataPassword";
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
        public static final  String COLUMN_IMAGE = "image";
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
}
