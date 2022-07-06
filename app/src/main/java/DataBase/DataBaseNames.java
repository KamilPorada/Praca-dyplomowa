package DataBase;

import android.provider.BaseColumns;


public interface DataBaseNames {

    public static final class TradeOfPepperItem implements BaseColumns{
        public static final String TABLE_NAME = "tradeOfPepperItem";
        public static final String COLUMN_COLOR_OF_PEPPER = "colorOfPepper";
        public static final String COLUMN_VAT = "vat";
        public static final String COLUMN_CLASS_OF_PEPPER = "classOfPepper";
        public static final String COLUMN_PRICE_OF_PEPPER = "priceOfPepper";
        public static final String COLUMN_WEIGHT_OF_PEPPER = "weightOfPepper";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_DATA_PASWORD = "dataPassword";
    }
    public static final class OutgoingsItem implements BaseColumns{
        public static final String TABLE_NAME = "outgoingsItem";
        public static final String COLUMN_CATEGORY_OF_OUTGOING = "categoryOfOutgoing";
        public static final String COLUMN_DESCRIBE_OF_OUTGOING = "describeOfOutgoing";
        public static final String COLUMN_PRICE_OF_OUTGOING = "priceOfOutgoing";
        public static final String COLUMN_DATE_OF_OUTGOING = "dateOfOutgoing";
        public static final String COLUMN_DATA_PASWORD = "dataPassword";
    }

}
