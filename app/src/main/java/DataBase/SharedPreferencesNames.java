package DataBase;

import HelperClasses.ToolClass;

public class SharedPreferencesNames {

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
