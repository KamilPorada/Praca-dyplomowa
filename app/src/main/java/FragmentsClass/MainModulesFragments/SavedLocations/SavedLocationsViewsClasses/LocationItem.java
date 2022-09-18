package FragmentsClass.MainModulesFragments.SavedLocations.SavedLocationsViewsClasses;

public class LocationItem {
    private int IId;
    private String ILocationName;
    private double ILatitude;
    private double ILongitude;

    public LocationItem(int id, String locationName, double latitude, double longitude) {
        this.IId = id;
        this.ILocationName = locationName;
        this.ILatitude = latitude;
        this.ILongitude = longitude;
    }

    public int getIId() {
        return IId;
    }

    public String getILocationName() {
        return ILocationName;
    }

    public double getILatitude() {
        return ILatitude;
    }

    public double getILongitude() {
        return ILongitude;
    }

}
