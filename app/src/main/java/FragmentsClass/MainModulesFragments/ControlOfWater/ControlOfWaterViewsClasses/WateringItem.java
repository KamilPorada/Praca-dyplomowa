package FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses;

public class WateringItem {
    private final int IId;
    private final double IEfficiency;
    private final String IDate;
    private final String IHighgroves;
    private final String ITime;
    private final String IUsageOfWater;
    private final int IStatus;

    public WateringItem(int id, double efficiency, String date, String highgroves, String time, String usageOfWater, int status) {
        this.IId = id;
        this.IEfficiency = efficiency;
        this.IDate = date;
        this.IHighgroves = highgroves;
        this.ITime = time;
        this.IUsageOfWater = usageOfWater;
        this.IStatus = status;
    }

    public int getIId() {
        return IId;
    }

    public double getIEfficiency() {
        return IEfficiency;
    }

    public String getIDate() {
        return IDate;
    }

    public String getIHighgroves() {
        return IHighgroves;
    }

    public String getITime() {
        return ITime;
    }

    public int getIStatus() {
        return IStatus;
    }

    public String getIUsageOfWater() {
        return IUsageOfWater;
    }
}
