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

    public void setIId(int IId) {
        this.IId = IId;
    }

    public String getILocationName() {
        return ILocationName;
    }

    public void setILocationName(String ILocationName) {
        this.ILocationName = ILocationName;
    }

    public double getILatitude() {
        return ILatitude;
    }

    public void setILatitude(double ILatitude) {
        this.ILatitude = ILatitude;
    }

    public double getILongitude() {
        return ILongitude;
    }

    public void setILongitude(double ILongitude) {
        this.ILongitude = ILongitude;
    }
}
