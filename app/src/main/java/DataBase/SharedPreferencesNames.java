package DataBase;

import HelperClasses.ToolClass;

public class SharedPreferencesNames {

    public static final class ToolSharedPreferences{
        public static final String NAME = "TOOL_SHARED_PREFERENCES";
        public static final String CATALOG_OF_PESTICIDE_OPEN_MODE = "CATALOG_OF_PESTICIDE_OPEN_MODE";
        public static final String POSITION_OF_OPERATION_RV = "POSITION_OF_OPERATION_RV";
        public static final String CHOSEN_PESTICIDE = "CHOSEN_PESTICIDE";
        public static final String SELECTED_PESTICIDES_RADIO_BUTTON = "SELECTED_PESTICIDES_RADIO_BUTTON";
        public static final String POSITION_OF_OUTGOING_RV = "POSITION_OF_OUTGOING_RV";
        public static final String OUTGOING_OPEN_MODE = "OUTGOING_OPEN_MODE";
        public static final String POSITION_OF_TRADE_RV = "POSITION_OF_TRADE_RV";
        public static final String TRADE_OF_PEPPER_OPEN_MODE = "TRADE_OF_PEPPER_OPEN_MODE";
        public static final String NOTE_OPEN_MODE = "NOTE_OPEN_MODE";
        public static final String POSITION_OF_NOTE_RV = "POSITION_OF_NOTE_RV";
        public static final String EFFICIENCY_OF_PUMP = "EFFICIENCY_OF_PUMP";
    }

    public static final class TemporaryCurrentOperations{
        public static final String NAME = "TEMPORARY_CURRENT_OPERATIONS";
        public static final String TYPE_OF_PESTICIDES = "TYPE_OF_PESTICIDES";
        public static final String DATA_OF_OPERATIONS = "DATA_OF_OPERATIONS";
        public static final String HOUR_OF_OPERATIONS = "HOUR_OF_OPERATIONS";
        public static final String AMOUNT_OF_HIGHGROVES = "AMOUNT_OF_HIGHGROVES";
        public static final String PESTICIDES = "PESTICIDES";
        public static final String ID_OF_PESTICIDES = "ID_OF_PESTICIDES";
        public static final String GRACE = "GRACE";
    }

    public static final class FarmData{
        public static final String NAME = "FARM_DATA";
        public static final String OWNER = "OWNER";
        public static final String HIGHGROVES = "HIGHGROVES";
        public static final String FIELD = "FIELD";
    }

    public static final class BasicData {
        public static final String NAME = "BASIC_DATA_"+ ToolClass.getActualYear();
        public static final String SEEDS = "SEEDS_DATA";
        public static final String PICKLING = "PICKLING_DATA";
        public static final String PLANT = "PLANT_DATA";
        public static final String IS_PLANT_EMPTY = "IS_PLANT_EMPTY";
        public static final String FIRST_PEPPER = "FIRST_PEPPER_DATA";
        public static final String LAST_PEPPER = "LAST_PEPPER_DATA";
    }
}
